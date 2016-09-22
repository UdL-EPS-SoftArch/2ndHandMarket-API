package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.PrivateMessage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface PrivateMessageRepository extends PagingAndSortingRepository<PrivateMessage, Long> {
}
