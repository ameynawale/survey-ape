package surveyape.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import surveyape.converters.Convertors;

import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class SessionValidator {

    @Value("${invalid.session.message}")
    String invalidSessionMsg;

    @Before("@annotation(CheckSession)")
    public void checkSession(JoinPoint joinPoint) throws Throwable {

        String sessionEmail = Convertors.fetchSessionEmail();
        if(sessionEmail == null) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), invalidSessionMsg);
        }
    }
}
