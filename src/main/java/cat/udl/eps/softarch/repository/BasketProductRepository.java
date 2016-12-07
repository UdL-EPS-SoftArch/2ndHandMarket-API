package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.BasketProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by carles on 29/11/16.
 */
@RepositoryRestResource
public interface BasketProductRepository  extends PagingAndSortingRepository<BasketProduct, Long> {
}
