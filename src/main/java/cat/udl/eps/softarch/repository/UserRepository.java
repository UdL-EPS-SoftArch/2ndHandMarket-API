package cat.udl.eps.softarch.repository;

import cat.udl.eps.softarch.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by jennifer on 28/09/16.
 */
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User,String>{
    List<User> findByName(@Param("name") String name);
}
