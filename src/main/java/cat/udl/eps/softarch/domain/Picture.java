package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="uri")
public class Picture extends UriEntity{

    @NotBlank
    private String filename;

    @Lob
    @Column(length = 5 * 1024 * 1024) // 5MB
    private String content;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement depicts;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    ZonedDateTime published;

    private String owner;


    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Advertisement getDepicts() { return depicts; }

    public void setDepicts(Advertisement depicts) { this.depicts = depicts; }

    public ZonedDateTime getPublished() { return published; }

    public void setPublished(ZonedDateTime published) { this.published = published; }

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    /**
     * Created by xavier on 22/09/16.
     */

    @Entity
    public static class Offer {

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
}
