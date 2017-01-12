package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="uri")
@JsonIgnoreProperties(value = {"purchaser", "createdAt", "total"}, allowGetters = true)
public class Purchase extends UriEntity {

    private User purchaser;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private Set<Advertisement> advertisements = new HashSet<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime createdAt;

    // Purchase total $. That is advertisement(s) price, or offer.
    @DecimalMin(message = "Total has to be bigger than 0.01", value = "0.01")
    private Double total;


    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
