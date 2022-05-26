package so.debateit.config;

public class UserNotiException extends RuntimeException {
    private String ErrorMsg;

    public String getErrorMsg(){
        return this.ErrorMsg;
    }

    public UserNotiException(String ErrorMsg) {
        super(ErrorMsg);
        this.ErrorMsg = ErrorMsg;
    }
}
