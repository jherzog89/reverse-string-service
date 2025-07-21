package herzog.webservices.restful.helloworld;

public class HelloWorldBean {

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

    public HelloWorldBean(String string) {
        this.message = string;
    }

}
