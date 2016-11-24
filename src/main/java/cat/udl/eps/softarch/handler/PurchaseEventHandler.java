package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Purchase;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler(Purchase.class)
public class PurchaseEventHandler {

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void handlePurchasePreCreate(Purchase purchase) {
        ZonedDateTime now = ZonedDateTime.now();
        purchase.setCreatedAt(now);
    }
}
