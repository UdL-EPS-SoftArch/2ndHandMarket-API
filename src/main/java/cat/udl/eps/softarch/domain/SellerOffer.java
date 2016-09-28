package cat.udl.eps.softarch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by julio on 22/09/16..
 */

@Entity
public class SellerOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sellerId;

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
}
