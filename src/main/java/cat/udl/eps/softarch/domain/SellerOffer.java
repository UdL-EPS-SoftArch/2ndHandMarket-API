package cat.udl.eps.softarch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by julio on 22/09/16..
 */

@Entity
public class SellerOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sellerId;

    /*Time variable in order to know the time when
    the offer was made*/
    private Date date;

    @NotBlank(message = "The offer cannot be null")
    private float value;

    public long getSellerId() {
        return sellerId;
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
