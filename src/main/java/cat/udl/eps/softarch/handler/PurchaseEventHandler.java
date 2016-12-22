package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.PrivateMessage;
import cat.udl.eps.softarch.domain.Purchase;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.PrivateMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.Set;

@Component
@RepositoryEventHandler(Purchase.class)
public class PurchaseEventHandler {
    @Autowired private AdvertisementRepository advertisementRepository;
    @Autowired private PrivateMessageRepository privateMessageRepository;

    private final String sellerNotificationTemplate = "Dear %s,\n\n" +
            "Your item %s has been sold.\n\n" +
            "Total:\n" +
            "$%f\n\n" +
            "Thank you for using our platform.";

    private final String buyerNotificationTemplate = "Dear %s,\n\n" +
            "You have just bought: %s.\n\n" +
            "Total:\n" +
            "$%s\n\n" +
            "Thank you for using our platform.";

    /**
     * Direct purchases only handler.
     * @param purchase
     */
    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void handlePurchasePreCreate(Purchase purchase) {
        String loggedInAs = SecurityContextHolder.getContext().getAuthentication().getName();

        ZonedDateTime now = ZonedDateTime.now();
        purchase.setCreatedAt(now);

        // The current purchaser; buying through accepting an offer will have a different handler.
        purchase.setPurchaser(loggedInAs);

        // A product cannot be purchased by their owner.
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            Advertisement dbAdvertisement = advertisementRepository.findOne(advertisement.getId());
            Assert.isTrue(!loggedInAs.equals(dbAdvertisement.getOwner()), "You cannot buy your own product.");
        }


        // A product can only be purchased once.
        // We are required to look into the repository since purchase.getAdvertisement() will have
        // already been edited when entering the handler.
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            Assert.isNull(advertisementRepository.findOne(advertisement.getId()).getPurchase(),
                    "This product has already been purchased.");
        }


        // Total is gathered from the advertisements that the user is purchasing.
        // Since no advertisement can cost 0, this also serves to control that the user is at least purchasing an item.
        double total = 0.0;
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            total += advertisement.getPrice();
        }
        Assert.isTrue(total > 0, "Total can't be 0.");
        purchase.setTotal(total);

        // Send a message to all users involved in the purchase.
        // For multiple advertisement purchases we'll group the buyer message items all in one.
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            PrivateMessage sellerNotification = new PrivateMessage();
            sellerNotification.setTitle("You have sold an item");
            sellerNotification.setBody(String.format(sellerNotificationTemplate, loggedInAs, advertisement.getTitle(), advertisement.getPrice()));
            sellerNotification.setSender("system");
            sellerNotification.setDestination(advertisement.getOwner());
            sellerNotification.setRead(false);
            privateMessageRepository.save(sellerNotification);
        }

        String titles = "";
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            titles += advertisement.getTitle() + ", ";
        }
        titles = titles.substring(0, titles.length() - 2);
        PrivateMessage buyerNotification = new PrivateMessage();
        buyerNotification.setTitle("You have bought an item");
        buyerNotification.setBody(String.format(buyerNotificationTemplate, loggedInAs, titles, total));
        buyerNotification.setSender("system");
        buyerNotification.setDestination(loggedInAs);
        buyerNotification.setRead(false);
        privateMessageRepository.save(buyerNotification);
    }

    @HandleAfterCreate
    public void handlePurchaseAfterCreate(Purchase purchase) {
        // Set advertisement -> purchase. For some reason it doesn't save it when set on purchase.
        // http://stackoverflow.com/a/19291538/2013580
        for (Advertisement advertisement: purchase.getAdvertisements()) {
            advertisement.setPurchase(purchase);
            advertisementRepository.save(advertisement);
        }
    }

    /**
     * Purchases are final, they cannot be edited nor deleted.
     */
    @HandleBeforeSave
    public void handlePurchasePreSave(Purchase purchase) {
        Assert.isTrue(false, "Purchases cannot be edited.");
    }

    @HandleBeforeDelete
    public void handlePurchasePreDelete(Purchase purchase) {
        Assert.isTrue(false, "Purchases cannot be deleted.");
    }
}
