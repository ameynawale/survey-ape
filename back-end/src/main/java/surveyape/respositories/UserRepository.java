package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.UserEntity;

/**
 * This class is responsible to perform the CRUD operations along with other custom operations on User Entity table
 *
 * @author Suhas Hunsimar
 *
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByEmailAndPassword (String email, String password);
}
