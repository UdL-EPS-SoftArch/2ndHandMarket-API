package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Carles on 22/09/2016.
 */
@Entity
public class RegisterSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String mail;
    private String password;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {

        this.mail = mail;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
