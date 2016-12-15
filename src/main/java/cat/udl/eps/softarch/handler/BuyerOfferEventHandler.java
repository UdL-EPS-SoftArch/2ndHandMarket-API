package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.BuyerOffer;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;

/**
 * Created by ierathenz on 30/11/16.
 */

@Component
@RepositoryEventHandler(BuyerOffer.class)
public class BuyerOfferEventHandler {

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void handleBuyerOfferPreCreate(BuyerOffer buyeroffer) {
        ZonedDateTime now = ZonedDateTime.now();
        buyeroffer.setDate(now);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        buyeroffer.setBuyer_id(username);
    }

    @HandleBeforeSave
    @Transactional
    public void handleBuyerOfferPreSave(BuyerOffer buyeroffer) {
        ZonedDateTime now = ZonedDateTime.now();
        buyeroffer.setDate(now);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        buyeroffer.setBuyer_id(username);
    }

    @HandleBeforeDelete
    @Transactional
    public void handleBuyerOfferPreDelete(BuyerOffer buyeroffer) {
//        Assert.isTrue(isOwner(buyeroffer), NOT_OWNER); // TODO
    }

    private String loggedInAs() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Boolean isOwner(BuyerOffer buyeroffer) {
        return buyeroffer.getBuyer_id().equals(loggedInAs());
    }
}
