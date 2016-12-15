package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.Purchase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {
    List<Purchase> findByAdvertisement(@Param("advertisement") Advertisement advertisement);
}

