package surveyape.exceptions;

public class HttpNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpNotFoundException(String statusNotFoundMsg) { super(statusNotFoundMsg); }
}