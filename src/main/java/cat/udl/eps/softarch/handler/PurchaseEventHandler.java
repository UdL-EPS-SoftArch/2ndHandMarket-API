package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.Purchase;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler(Purchase.class)
public class PurchaseEventHandler {
    @Autowired private AdvertisementRepository advertisementRepository;

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

        // A product cannot be purchased by their owner
        Assert.isTrue(!loggedInAs.equals(purchase.getAdvertisement().getOwner()),
                      "You cannot buy your own product.");

        // A product can only be purchased once.
        // We are required to look into the repository since purchase.getAdvertisement() will have
        // already been edited when entering the handler.
        Advertisement purchasing = purchase.getAdvertisement();
        Assert.isNull(advertisementRepository.findOne(purchasing.getId()).getPurchase(),
                      "This product has already been purchased.");
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
