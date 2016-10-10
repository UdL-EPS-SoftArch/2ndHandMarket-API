package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by julio on 22/09/16..
 */

@Entity
public class SellerOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "The offer cannot be null")
    private float value;

    /*Time variable in order to know the time when
    the offer was made*/

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime publish_date;

    /*Advertisement object in order add functionalities to tests*/
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement advert;
    private String agent;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
            this.value = value;
    }

    public Advertisement getAdvertisement(){
        return advert;
    }

    public void setAdvertisement(Advertisement advert){
        this.advert = advert;
    }

    public void setDate(ZonedDateTime date){
        this.publish_date = publish_date;
    }

    public ZonedDateTime getDate() {
        return publish_date;
    }

    public void dateToString() {
        System.out.println(publish_date.toString());
    }
}

