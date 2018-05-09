package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.SurveyEntity;
import surveyape.entity.UserEntity;
import surveyape.models.Survey;

import java.util.Set;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> {

    SurveyEntity findBySurveyid(String surveyId);
    Set<SurveyEntity> findAllByUserEntity(UserEntity userEntity);
    Set<SurveyEntity> findAllBySurveytype(String surveytype);
}
