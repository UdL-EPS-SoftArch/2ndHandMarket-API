package cat.udl.eps.softarch.handler;

import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
@RepositoryEventHandler(User.class)
public class UserHandler {
    @Autowired private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * User validation and such is already done elsewhere. What's left is to encrypt passwords that are sent by the
     * user.
     */
    @HandleBeforeCreate
    @Transactional
    public void handleUserPreCreate(User user) {
        Assert.isNull(userRepository.findOne(user.getUsername()));

        if (user.getPassword() != null)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    /**
     * Password encrypting is also required on creation.
     */
    @HandleBeforeSave
    @Transactional
    public void handleUserPreSave(User user) {
        if (user.getPassword() != null)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // For some reason, PreSave doesn't call setters.
        user.setName(user.getName());
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('USER')")
    public void handleUserPreDelete(User user) {
        String loggedInAs = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(loggedInAs.equals(user.getName()));
    }
}
