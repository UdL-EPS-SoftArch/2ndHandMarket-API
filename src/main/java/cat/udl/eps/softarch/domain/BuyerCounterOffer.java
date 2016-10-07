package cat.udl.eps.softarch.domain;

/**
 * Created by xavier on 26/09/16.
 */
public class BuyerCounterOffer extends Offer {

    private String  agent;
    private SellerOffer respondsTo;

    @Override
    public void setValue(float value){
        if (value < this.getValue()){
            System.out.println("As a buyer, you want to set a lower price");
        }
        else{
            super.setValue(value);
        }
    }

    public String getAgent(){
        return agent;
    }

    public void setAgent(String agent){
        this.agent = agent;
    }

    public SellerOffer getRespondsTo(){
        return respondsTo;
    }

    public void setRespondsTo(SellerOffer respondsTo){
        this.respondsTo = respondsTo;
    }
}
