package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

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

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement advert;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime published;

    public Long getId(){
        return id;
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

   public void setAdverttisement(Advertisement advert){
       this.advert = advert;
   }

   public ZonedDateTime getData(){
        System.out.println(published);
        return published;
   }
}
