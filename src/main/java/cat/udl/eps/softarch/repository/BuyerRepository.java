package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.Buyer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jennifer on 27/09/16.
 */
@RepositoryRestResource
public interface BuyerRepository extends PagingAndSortingRepository<Buyer,Long> {
}
