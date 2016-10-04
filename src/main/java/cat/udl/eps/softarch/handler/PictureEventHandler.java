package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@RepositoryEventHandler(Picture.class)
class PictureEventHandler {
    private final Logger logger = LoggerFactory.getLogger(PictureEventHandler.class);

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void handlePicturePreCreate(Picture picture) {
        logger.info("Before creating: {}", picture.getFilename());

        picture.setPublished(ZonedDateTime.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        picture.setOwner(username);
    }

    @HandleBeforeSave
    @Transactional
    public void handlePicturePreSave(Picture picture){
        logger.info("Before updating: {}", picture.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handlePicturePreDelete(Picture picture){
        logger.info("Before deleting: {}", picture.toString());
    }

    @HandleBeforeLinkSave
    public void handlePicturePreLinkSave(Picture picture, Object o) {
        logger.info("Before linking: {} to {}", picture.toString(), o.toString());
    }

    @HandleAfterCreate
    @Transactional
    public void handlePicturePostCreate(Picture picture){
        logger.info("After creating: {}", picture.toString());
    }

    @HandleAfterSave
    @Transactional
    public void handlePicturePostSave(Picture picture){
        logger.info("After updating: {}", picture.toString());
    }

    @HandleAfterDelete
    @Transactional
    public void handlePicturePostDelete(Picture picture){
        logger.info("After deleting: {}", picture.toString());
    }

    @HandleAfterLinkSave
    public void handlePicturePostLinkSave(Picture picture, Object o) {
        logger.info("After linking: {} to {}", picture.toString(), o.toString());
    }
}
