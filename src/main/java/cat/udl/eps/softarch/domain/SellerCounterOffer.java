package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/**
 * Created by julio on 28/09/16.
 */

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class SellerCounterOffer extends SellerOffer {

    @JsonIdentityReference(alwaysAsId=true)
    @OneToOne
    private BuyerCounterOffer respondsTo;

    public BuyerCounterOffer getRespondsTo(){ return respondsTo; }

    public void setRespondsTo(BuyerCounterOffer respondsTo){ this.respondsTo = respondsTo; }

}
