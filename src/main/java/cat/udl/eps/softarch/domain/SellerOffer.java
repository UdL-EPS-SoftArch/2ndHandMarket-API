package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;

/**
 * Created by julio on 22/09/16..
 */

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class SellerOffer extends Offer {

}

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
            this.value = value;
    }

    public void setDate(Date date){
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void dateToString() {
        System.out.println(date.toString());
    }
}
