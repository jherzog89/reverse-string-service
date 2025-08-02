package herzog.webservices.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import herzog.webservices.restful.model.ManipulatedString;
import herzog.webservices.restful.service.StringManipulationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StringManipulationRest {

    @Autowired
    private StringManipulationService srv;

    public StringManipulationService getSrv() {
        return srv;
    }

    public void setSrv(StringManipulationService srv) {
        this.srv = srv;
    }

    @PostMapping("/reverseString")
   // @Retry(name="default")
   //@Bulkhead(name="default")
    @RateLimiter(name="default")
    public List<ManipulatedString> reverseString(HttpServletRequest request, @RequestBody ManipulatedString strToBeReversed){
            System.out.println("Processing a POST in /reverseString");
            strToBeReversed.setApiUsed(request.getRequestURL().toString());
           strToBeReversed = srv.reverseString(strToBeReversed);
  
        return srv.getLastTenReversedStrings();//new ResponseEntity<>(srv.getLastTenReversedStrings(), HttpStatus.CREATED);
    }

    //watch -n 0.1 http GET http://localhost:8765/reverseString
    @GetMapping("/reverseString")
    //@Retry(name="default")
     //@Bulkhead(name="default")
    @RateLimiter(name="default")
    public List<ManipulatedString> getLastTenReversedStrings(){
        System.out.println("Processing a GET in /reverseString");
        return srv.getLastTenReversedStrings();
    } 

    //DELETE
    @DeleteMapping("/reverseString")
    //@Retry(name="default")
     //@Bulkhead(name="default")
    @RateLimiter(name="default")
    public List<ManipulatedString> deleteAllManipulatedStrings(){
        System.out.println("Processing a DELETE in /reverseString");

        srv.deleteAllManipulatedStrings();

        return srv.getLastTenReversedStrings();

    }
}
