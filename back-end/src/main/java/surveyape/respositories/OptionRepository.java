package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.OptionsEntity;
import surveyape.entity.QuestionsEntity;

import java.util.Set;

public interface OptionRepository extends CrudRepository<OptionsEntity, Long>{
    Set<OptionsEntity> findAllByQuestionsEntity(QuestionsEntity questionsEntity);
}
