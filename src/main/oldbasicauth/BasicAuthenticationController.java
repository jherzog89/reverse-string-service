package herzog.webservices.restful;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//controller
@CrossOrigin(origins="*")
@RestController
public class BasicAuthenticationController { 

    @GetMapping(path="/basicauth")
    public AuthenticationBean helloWorldBean(){
        return new AuthenticationBean("You are authenticated");
    }
}
