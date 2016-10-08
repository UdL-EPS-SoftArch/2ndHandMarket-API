package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by jap9 on 22/09/16.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class PrivateMessage extends UriEntity{

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

