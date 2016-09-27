package cat.udl.eps.softarch.domain;

/**
 * Created by xavier on 26/09/16.
 */
public class BuyerOffer extends Offer {
    @Override
    public void setValue(float value){
        if (value >= this.getValue()){
            System.out.println("As a buyer, you want to set a lower price");
        }
        else{
            super.setValue(value);
        }
    }
}
