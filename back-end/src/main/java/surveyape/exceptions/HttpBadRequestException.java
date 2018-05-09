package surveyape.exceptions;

public class HttpBadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpBadRequestException(String badRequestMsg) { super(badRequestMsg); }
}