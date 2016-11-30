package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
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

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler(Advertisement.class)
public class AdvertisementEventHandler {
    @Autowired private AdvertisementRepository advertisementRepository;

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
    }

    @HandleBeforeDelete
    @Transactional
    @PreAuthorize("#advertisement.owner == authentication.name")
    public void handleAdvertisementPreDelete(Advertisement advertisement) {
    }
}
