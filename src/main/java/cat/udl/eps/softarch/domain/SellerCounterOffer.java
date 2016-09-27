package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by julio on 22/09/16..
 */

@Entity
public class SellerCounterOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(message = "value cannot be null")

    private long id;
    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if (this.value>value){
            System.out.println("The new seller offer must be lower than the stablished price");
        } else{
            this.value = value;
        }
    }
}
