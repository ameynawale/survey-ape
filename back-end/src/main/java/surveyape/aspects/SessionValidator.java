package surveyape.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import surveyape.converters.Convertors;
import surveyape.exceptions.InvalidSessionException;


@Aspect
@Component
public class SessionValidator {

    @Before("@annotation(CheckSession)")
    public void checkSession(JoinPoint joinPoint) {
        String sessionEmail = Convertors.fetchSessionEmail();
        if(sessionEmail == null) throw new InvalidSessionException("Invalid Session!");
    }
}
