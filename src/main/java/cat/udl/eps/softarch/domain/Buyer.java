package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jennifer on 27/09/16.
 */
@Entity
public class Buyer extends User{

    @ElementCollection
    private List<String> products;
    private int count=0;


    public List<String> getProducts(){
        return products;
    }

    public void setProducts(List<String> products){
        this.products=products;
        count+=1;
    }

    public int getCount(){
        return count;
    }
}
