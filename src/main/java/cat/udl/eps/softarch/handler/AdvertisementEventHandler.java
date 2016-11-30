package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.Purchase;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.PurchaseRepository;
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
import java.util.List;

@Component
@RepositoryEventHandler(Advertisement.class)
public class AdvertisementEventHandler {
    @Autowired private AdvertisementRepository advertisementRepository;
    @Autowired private PurchaseRepository purchaseRepository;

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void handleAdvertisementPreCreate(Advertisement advertisement) {
        ZonedDateTime now = ZonedDateTime.now();
        advertisement.setCreatedAt(now);
        advertisement.setModifiedAt(now);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        advertisement.setOwner(username);
    }

    @HandleBeforeSave
    @Transactional
    @PreAuthorize("#advertisement.owner == authentication.name")
    public void handleAdvertisementPreSave(Advertisement advertisement) {
        // Note: owner and createdAt cannot be changed through API because setters are @JsonIgnore
        ZonedDateTime now = ZonedDateTime.now();
        advertisement.setModifiedAt(now);

        // A purchased advertisement cannot be edited
        List<Purchase> dbPurchase = purchaseRepository.findByAdvertisement(advertisement);
        Assert.isTrue(dbPurchase.size() == 0, "A purchased advertisement cannot be edited");
    }

    @HandleBeforeDelete
    @Transactional
    @PreAuthorize("#advertisement.owner == authentication.name")
    public void handleAdvertisementPreDelete(Advertisement advertisement) {
        // A purchased advertisement cannot be deleted
        Advertisement dbAdvertisement = advertisementRepository.findOne(advertisement.getId());
        Assert.isNull(dbAdvertisement.getPurchase(), "A purchased advertisement cannot be deleted");
    }
}
