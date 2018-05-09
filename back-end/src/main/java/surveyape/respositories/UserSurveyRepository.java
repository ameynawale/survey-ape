package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.UserSurveyEntity;

public interface UserSurveyRepository extends CrudRepository<UserSurveyEntity, Long> {

    UserSurveyEntity findBySurveyidAndEmail(String surveyid, String email);
    UserSurveyEntity findBySurveyid(String surveyid);
    UserSurveyEntity findByEmail(String email);
}