package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by xavier on 22/09/16.
 */

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class Offer extends UriEntity{


    /*@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    //@NotBlank(message = "The offer cannot be null")

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement advert;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime published;
    private User agent;
    private float value = 0;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Advertisement getAdvertisement(){
        return advert;
    }

    public void setAdverttisement(Advertisement advert){
       this.advert = advert;
   }

    public User getBuyer(){
        return agent;
    }

    public void setBuyer(User agent){
        this.agent = agent;
    }

    public ZonedDateTime getDate(){
        System.out.println(published);
        return published;
    }
}
