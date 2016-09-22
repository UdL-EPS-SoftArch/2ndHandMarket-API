package cat.udl.eps.softarch.domain;

/**
 * Created by jap9 on 22/09/16.
 */
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Body cannot be blank")
    private String body;

    @NotBlank(message = "Destination cannot be blank")
    private String destination;

    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getDestination() {
    return destination;
}

}

