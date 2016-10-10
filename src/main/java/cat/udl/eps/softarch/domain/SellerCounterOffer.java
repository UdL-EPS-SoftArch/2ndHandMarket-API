package cat.udl.eps.softarch.domain;

import javax.persistence.*;

/**
 * Created by julio on 28/09/16.
 */

@Entity
public class SellerCounterOffer extends SellerOffer {

    @ManyToOne
    private BuyerCounterOffer respondsTo;

    private float newValue;

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

    public BuyerCounterOffer getRespondsTo(){
        return respondsTo;
    }

    public void setRespondsTo(BuyerCounterOffer respondsTo){
        this.respondsTo = respondsTo;
    }

}
