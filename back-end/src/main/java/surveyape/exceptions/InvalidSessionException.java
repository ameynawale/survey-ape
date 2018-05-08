package surveyape.exceptions;

public class InvalidSessionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidSessionException(String invalidSessionMsg) { super(invalidSessionMsg); }
}
