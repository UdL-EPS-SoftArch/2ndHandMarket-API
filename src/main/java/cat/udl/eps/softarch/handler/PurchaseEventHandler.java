package cat.udl.eps.softarch.handler;

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

import javax.validation.constraints.AssertTrue;
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
        ZonedDateTime now = ZonedDateTime.now();
        purchase.setCreatedAt(now);

        // The current purchaser; buying through accepting an offer will have a different handler.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        purchase.setPurchaser(username);

        // A product can only be purchased once.
//        Assert.isNull(advertisementRepository.findOne(purchase.getAdvertisement().getId()));
    }

    /**
     * Purchases are final, they cannot be edited nor deleted.
     */
    @HandleBeforeSave
    public void handlePurchasePreSave(Purchase purchase) {
        throw new AssertionError("Purchases cannot be edited");
    }

    @HandleBeforeDelete
    public void handlePurchasePreDelete(Purchase purchase) {
        throw new AssertionError("Purchases cannot be deleted");
    }
}
