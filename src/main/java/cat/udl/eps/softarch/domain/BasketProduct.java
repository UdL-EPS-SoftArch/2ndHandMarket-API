package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by carles on 29/11/16.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class BasketProduct extends UriEntity {

    private String title;
    private Double price;



    public void setTitle(String title) {
        this.title = title;
    }
    public void setPrice(Double price){this.price = price;}

    public String getTitle() {
        return title;
    }
    public Double getPrice() {
        return price;
    }


}
