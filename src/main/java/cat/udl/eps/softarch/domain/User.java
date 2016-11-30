package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "SoftArchUser") //Avoid collision with system table User in Postgres
public class User implements UserDetails {

    @Id
    private String username;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String lastname;
    private String email;
    private Date birthday;
    private String country;
    @NotEmpty(message = "Is necessary a password")
    private String password;

    public void setName (String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getEmail(){
        return email;
    }

    public void setBirthday(Date birthday){
        this.birthday=birthday;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public String getCountry(){
        return country;
    }

    @Override
    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    @Override
    @JsonIgnore
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }
}
