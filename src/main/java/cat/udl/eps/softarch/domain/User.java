package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jennifer on 28/09/16.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String lastname;
    private String username;
    private String email;
    private Date birthday;
    private String country;
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

    public void setUsername(String username) {
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public void setEmail(String mail){
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

    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
}
