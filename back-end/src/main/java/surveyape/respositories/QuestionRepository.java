package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.QuestionsEntity;

public interface QuestionRepository extends CrudRepository<QuestionsEntity, Long> {
//    QuestionsEntity findById(int questionid);
}
