package herzog.webservices.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import herzog.webservices.restful.model.ManipulatedString;
import herzog.webservices.restful.service.StringManipulationService;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
public class StringManipulationRest {

    @Autowired
    private StringManipulationService srv;

    @PostMapping("/reverseString")
    public List<ManipulatedString> reverseString(HttpServletRequest request, @RequestBody ManipulatedString strToBeReversed){
            System.out.println("Processing a POST in /reverseString");
            strToBeReversed.setApiUsed(request.getRequestURL().toString());
           strToBeReversed = srv.reverseString(strToBeReversed);
  
        return srv.getLastTenReversedStrings();//new ResponseEntity<>(srv.getLastTenReversedStrings(), HttpStatus.CREATED);
    }

    @GetMapping("/reverseString")
    public List<ManipulatedString> getLastTenReversedStrings(){
        System.out.println("Processing a GET in /reverseString");
        return srv.getLastTenReversedStrings();
    } 

    //DELETE
    @DeleteMapping("/reverseString")
    public List<ManipulatedString> deleteAllManipulatedStrings(){
        System.out.println("Processing a DELETE in /reverseString");

        srv.deleteAllManipulatedStrings();

        return srv.getLastTenReversedStrings();

    }


    /*
     * 
     *     @PostMapping("/users/{username}/todos")
    public ResponseEntity<Void> createTodo(@PathVariable String username,  
    @RequestBody Todo todo){
        Todo createdTodo = todoService.save(todo);
 
         URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
     */
}
