package surveyape.exceptions;

public class HttpUnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpUnAuthorizedException(String unauthorizedMsg) { super(unauthorizedMsg); }
}