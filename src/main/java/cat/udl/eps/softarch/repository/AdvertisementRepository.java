package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.Advertisement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by http://rhizomik.net/~roberto/
 */

@Transactional
@RepositoryRestResource
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Long> {

    List<Advertisement> findByTagsIn(@Param("tag") List<String> tags);

    List<Advertisement> findByTitleContaining(@Param("word") String word);

    List<Advertisement> findByCategory(@Param("category") String category);
}
