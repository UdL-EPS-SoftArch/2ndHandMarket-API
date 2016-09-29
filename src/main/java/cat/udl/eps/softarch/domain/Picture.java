package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

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
    private String image;

    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne
    private Advertisement depicts;

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Advertisement getDepicts() { return depicts; }

    public void setDepicts(Advertisement depicts) { this.depicts = depicts; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
