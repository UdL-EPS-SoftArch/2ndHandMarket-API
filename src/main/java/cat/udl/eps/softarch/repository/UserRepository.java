package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by jennifer on 28/09/16.
 */
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User,Long>{
    User findById(Long id);
    List<User> findByName(String name);
}
