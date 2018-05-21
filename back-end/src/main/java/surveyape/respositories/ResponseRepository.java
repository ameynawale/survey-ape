package surveyape.respositories;

import org.springframework.data.repository.CrudRepository;
import surveyape.entity.QuestionsEntity;
import surveyape.entity.ResponseEntity;

import java.util.Set;


public interface ResponseRepository extends CrudRepository<ResponseEntity, Long> {
    ResponseEntity findByDummyid(Long id);
    Set<ResponseEntity> findByQuestionsEntity(QuestionsEntity questionsEntity);
    ResponseEntity findByEmailAndAndQuestionsEntity(String email, QuestionsEntity questionsEntity);
    ResponseEntity findByEmailAndAndQuestionsEntityAndOptionid(String email, QuestionsEntity questionsEntity, Long optionid);
    long countByQuestionsEntityAndOptionid(QuestionsEntity questionsEntity, Long optionid);
    long countByQuestionsEntity(QuestionsEntity questionsEntity);
}
