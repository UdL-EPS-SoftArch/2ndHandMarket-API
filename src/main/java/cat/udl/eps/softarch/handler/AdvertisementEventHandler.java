package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Advertisement;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler(Advertisement.class)
public class AdvertisementEventHandler {

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
}
