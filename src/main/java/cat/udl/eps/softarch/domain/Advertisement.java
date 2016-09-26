package cat.udl.eps.softarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Keywords cannot be blank")
    private String keyWords;

    public String getKeyWords(){ return keyWords;}

    public void setKeyWords(String keyWords){
        this.keyWords = keyWords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
