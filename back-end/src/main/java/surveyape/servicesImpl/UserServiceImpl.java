package surveyape.servicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.UserEntity;
import surveyape.models.User;
import surveyape.respositories.UserRepository;
import surveyape.services.MailService;
import surveyape.services.UserService;

import java.util.Random;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public String fetchUniqueUser(User user) {
        if(user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null)  return "USED_EMAIL";
        else                                                                                return "AVAILABLE";
    }

    public User addUser(User user){

        /*int randomNumber = (int)Math.random() * ((9999 - 1000) + 1) + 1000;
        String code = Integer.toString(randomNumber);*/
        Random rand = new Random();
        String id = String.format("%04d", rand.nextInt(10000));
        System.out.println(id);
       // System.out.println(mailService.getCode());
        UserEntity userEntity = new UserEntity(user.getFirstname(),user.getLastname(),
                user.getEmail(),user.getPassword(),0, id);
        userEntity = userRepository.save(userEntity);

        return Convertors.mapUserEntityToUser(userEntity);
    }

    public User getUser(String email){
        UserEntity user = userRepository.findByEmail(email);
        if(user!=null){
            return Convertors.mapUserEntityToUser(user);
        } else{
            return null;
        }
    }

    public User login(User user) {

        UserEntity userEntity = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(userEntity!=null){
            return Convertors.mapUserEntityToUser(userEntity);
        } else{
            return null;
        }
    }

    public void activateUser(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity!=null){
            userEntity.setIsactivated(1);
            userRepository.save(userEntity);
        }
    }
}
