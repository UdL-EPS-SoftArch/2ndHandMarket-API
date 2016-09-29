package cat.udl.eps.softarch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.atteo.evo.inflector.English;
import org.springframework.data.domain.Persistable;
import org.springframework.util.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@MappedSuperclass
public abstract class UriEntity implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uri;

    @Override
    public Long getId() { return id; }

    public String getUri() {
        return "/" + English.plural(StringUtils.uncapitalize(this.getClass().getSimpleName())) + "/" + id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() { return id == null; }
}
