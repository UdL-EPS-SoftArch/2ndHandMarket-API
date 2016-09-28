package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by xavier on 26/09/16.
 */

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class BuyerCounterOffer extends Offer {

    @JsonIdentityReference(alwaysAsId=true)
    @OneToOne
    private SellerOffer respondsTo;

    public SellerOffer getRespondsTo(){
        return respondsTo;
    }

    public void setRespondsTo(SellerOffer respondsTo){
        this.respondsTo = respondsTo;
    }
}
