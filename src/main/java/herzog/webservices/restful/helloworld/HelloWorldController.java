package herzog.webservices.restful.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//controller
//@CrossOrigin(origins="*")
@RestController
public class HelloWorldController {

    //GET
    //URI - /hello-world
    //method - "Hello World"
    @GetMapping(path="/hello-world")
    public String helloWorld(){
        return "Hellow World";
    }
 

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }
    ///hello-world-bean/path-variable/in28minutes
    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
