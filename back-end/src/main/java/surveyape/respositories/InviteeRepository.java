package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.InviteesEntity;

public interface InviteeRepository extends CrudRepository<InviteesEntity, Long> {
}
