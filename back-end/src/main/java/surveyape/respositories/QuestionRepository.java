package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.QuestionsEntity;
import surveyape.entity.SurveyEntity;

import java.util.Set;

public interface QuestionRepository extends CrudRepository<QuestionsEntity, Long> {
    QuestionsEntity findById(long questionid);
    Set<QuestionsEntity> findBySurveyEntity(SurveyEntity surveyEntity);
}
