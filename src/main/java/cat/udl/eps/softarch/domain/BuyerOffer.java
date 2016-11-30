package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by julio on 23/11/16.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class BuyerOffer extends UriEntity{

    private String buyer_id;

    private String advertisement_id;

    private Double value;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime date;

    public String getBuyer_id() {
        return buyer_id;
    }

    public String getAdvertisement_id() {
        return advertisement_id;
    }

    public Double getValue() {
        return value;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setAdvertisement_id(String advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

}