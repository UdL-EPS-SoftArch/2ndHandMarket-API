package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler(Advertisement.class)
public class AdvertisementEventHandler {
    private static final String NOT_OWNER = "You are not the owner";

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
    public void handleAdvertisementPreSave(Advertisement advertisement) {
//        Assert.isTrue(isOwner(advertisement), NOT_OWNER); // TODO
        ZonedDateTime now = ZonedDateTime.now();
        advertisement.setModifiedAt(now);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        advertisement.setOwner(username);
    }

    @HandleBeforeDelete
    @Transactional
    public void handleAdvertisementPreDelete(Advertisement advertisement) {
//        Assert.isTrue(isOwner(advertisement), NOT_OWNER); // TODO
    }

    private String loggedInAs() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Boolean isOwner(Advertisement advertisement) {
        return advertisement.getOwner().equals(loggedInAs());
    }
}
