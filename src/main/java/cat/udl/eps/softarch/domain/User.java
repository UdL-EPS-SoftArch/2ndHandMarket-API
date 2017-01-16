package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.*;
import org.atteo.evo.inflector.English;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
@Table(name = "SoftArchUser") //Avoid collision with system table User in Postgres
public class User implements UserDetails {

    @Id
    private String username;
    private String uri;
    private String displayName;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String lastname;
    private String email;
    private Date birthday;
    private String country;
    private String password;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Advertisement> wishes = new HashSet<>();

    @OneToMany(mappedBy = "purchaser", fetch = FetchType.EAGER)
    private Set<Purchase> purchases = new HashSet<>();

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Set<Advertisement> getWishes() {
        return wishes;
    }

    public void setWishes(Set<Advertisement> wishes) {
        this.wishes = wishes;
    }

    public void addAdvertisement(Advertisement advertisement){
        this.wishes.add(advertisement);
    }

    public String getUri() {
        return "/" + English.plural(StringUtils.uncapitalize(this.getClass().getSimpleName())) + "/" + username;
    }

    public void setName (String name){
        this.name=name;

        // Display name defaults to user name.
        if (this.getDisplayName() == null || this.getDisplayName().isEmpty()) {
            this.setDisplayName(this.getName());
        }
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
    public String getPassword(){ return password; }

    @JsonSetter
    public void setPassword(String password){ this.password=password; }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}