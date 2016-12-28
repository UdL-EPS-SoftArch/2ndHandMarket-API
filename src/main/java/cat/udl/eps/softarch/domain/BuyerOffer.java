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

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement advOffers;

    private String buyer_id;

    private int advertisement_id;

    private String advertisement_title;

    private String advertisement_seller;

    private int advertisement_iniPrice;

    private Double value;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime date;

    public String getBuyer_id() {
        return buyer_id;
    }

    public int getAdvertisement_id() {
        return advertisement_id;
    }

    public String getAdvertisement_title() {
        return advertisement_title;
    }

    public String getAdvertisement_seller() {
        return advertisement_seller;
    }

    public int getAdvertisement_iniPrice() {
        return advertisement_iniPrice;
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

    public void setAdvertisement_id(int advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public void setAdvertisement_title(String advertisement_title) {
        this.advertisement_title = advertisement_title;
    }

    public void setAdvertisement_seller(String advertisement_seller) {
        this.advertisement_seller = advertisement_seller;
    }

    public void setAdvertisement_iniPrice(int advertisement_iniPrice) {
        this.advertisement_iniPrice = advertisement_iniPrice;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Advertisement getAdvOffers() { return advOffers; }

    public void setAdvOffers(Advertisement advOffers) { this.advOffers = advOffers; }


}