package surveyape.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.UserEntity;
import surveyape.models.User;
import surveyape.respositories.UserRepository;
import surveyape.services.UserService;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){

        int randomNumber = (int)Math.random() * ((9999 - 1000) + 1) + 1000;
        String code = Integer.toString(randomNumber);
        System.out.println(code);
        UserEntity userEntity = new UserEntity(user.getFirstname(),user.getLastname(),
                user.getEmail(),user.getPassword(),0, code);
        userEntity = userRepository.save(userEntity);

        return Convertors.mapUserEntityToUser(userEntity);
    }

    public Boolean getUser(String email){
        UserEntity user = userRepository.findByEmail(email);
        if(user!=null){
            return true;
        } else{
            return false;
        }
    }

    public User login(User user){
        UserEntity userEntity = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(userEntity!=null){
            return Convertors.mapUserEntityToUser(userEntity);
        } else{
            return null;
        }
    }
}
