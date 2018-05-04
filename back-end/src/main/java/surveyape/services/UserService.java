package surveyape.services;


import surveyape.models.User;

/**
 * UserService provides the business logic for all the user operations
 * 
 * @author Suhas Hunsimar
 *
 */
public interface UserService {
    User addUser(User user);

    Boolean getUser(String email);

    User login(User user);
}
