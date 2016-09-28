package cat.udl.eps.softarch.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by julio on 28/09/16.
 */
public class SellerCounterOffer extends SellerOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sellerCounterOfferId;

    private float newValue;

    public long getSellerCounterOfferId() {
        return sellerCounterOfferId;
    }

    @Override
    public long getSellerId (){
        return super.getSellerId();
    }

    @Override
    public float getValue () {
        return super.getValue();
    }

    @Override
    public void setValue(float newValue){
        if (newValue <= this.getValue()){
            System.out.println("As a seller, you want to set an upper price");
        }
        else{
            super.setValue(newValue);
        }
        offerAlert();
    }

    public void offerAlert() {
        System.out.println("The product offer had been changed to a new value: " + getValue());
    }
}
