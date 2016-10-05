package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xavier on 22/09/16.
 */

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The offer cannot be null")
    private float value = 0;
    //private Advertisement advert = new Advertisement();
    private java.util.Date date= new java.util.Date();
    private Timestamp time = new Timestamp(date.getTime());

    public Long getId(){
        return id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

   /*public Advertisement getAdvertisement(){
        return advert;
    }*/

    public Timestamp getData(){
        System.out.println(time);
        return time;
    }
}
