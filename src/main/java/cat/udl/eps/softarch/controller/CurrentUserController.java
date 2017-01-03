package cat.udl.eps.softarch.controller;

import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.LinkedHashMap;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Controller
public class CurrentUserController {

    @Autowired UserRepository userRepository;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    UserDetails getCurrentUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody User getCurrentUser(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        LinkedHashMap details = (LinkedHashMap) oAuth2Authentication.getUserAuthentication().getDetails();
        String userId = (String) details.get("user_id");
        User user;
        if (userRepository.exists(userId))
            user = userRepository.findOne(userId);
        else {
            user = new User();
            user.setUsername(userId);
            if (details.containsKey("given_name")) {
                user.setName((String) details.get("given_name"));
                user.setLastname((String) details.get("family_name"));
            }
            else
                user.setName((String) details.get("nickname"));
            user = userRepository.save(user);
        }
        return user;
    }
}
