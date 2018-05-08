package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.OptionsEntity;

public interface OptionRepository extends CrudRepository<OptionsEntity, Long>{
}
