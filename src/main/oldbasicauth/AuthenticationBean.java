package herzog.webservices.restful;

public class AuthenticationBean {

    private String message;

    @Override
    public String toString() {
        return "HelloWorldBean [message=" + message + ", toString()=" + super.toString() + "]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthenticationBean(String string) {
        this.message = string;
    }

}
