package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.Purchase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {
}

