package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Created by xavier on 22/09/16.
 */

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class Offer extends UriEntity {

    @NotNull(message = "The offer cannot be null")
    private float value;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime published;

    private String agent;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement advertisement;

    public Advertisement getAdvertisement(){ return advertisement; }

    public void setAdvertisement(Advertisement advertisement){ this.advertisement = advertisement; }

    public String getAgent(){ return agent; }

    public void setAgent(String agent){
        this.agent = agent;
    }

    public float getValue() { return value; }

    public void setValue(float value) { this.value = value; }

    public ZonedDateTime getPublished(){ return published; }

    public void setPublished(ZonedDateTime published){ this.published = published; }
}
