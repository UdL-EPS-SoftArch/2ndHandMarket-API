package cat.udl.eps.softarch.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;


/**
 * Created by jennifer on 27/09/16.
 */
@Entity
public class Buyer extends User{

    @ElementCollection
    private List<String> products;

    public List<String> getProducts(){
        return products;
    }

    public void setProducts(List<String> products){
        this.products=products;
    }

    public int getCount(){
        return products.size();
    }
}
