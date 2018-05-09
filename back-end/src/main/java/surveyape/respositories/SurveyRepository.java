package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.SurveyEntity;
import surveyape.entity.UserEntity;

import java.util.Set;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> {

    SurveyEntity findBySurveyid(String surveyid);
    Set<SurveyEntity> findAllByUserEntity(UserEntity userEntity);
}
