package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.SurveyEntity;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> {
    SurveyEntity findBySurveyid(int surveyId);
}
