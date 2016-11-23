package cat.udl.eps.softarch.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Controller
public class CurrentUserController {

    @RequestMapping(value="/login", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody UserDetails getCurrentUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
