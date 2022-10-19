package lorenz.example.minicapstone.controller;

import lorenz.example.minicapstone.model.TodoModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("helloWorld")
public class getController {

    @GetMapping("")
    public String getHelloWorld(){
        return "Hello World!";
    }

    @PostMapping("/custom")
    public String getHelloUser(@RequestBody TodoModel todoModel) {
        return "Hello " .concat(todoModel.getFirstName()).concat(" ").concat(todoModel.getLastName());
    }

    @PutMapping("/custom/{firstName}/{lastName}")
    public String getHelloUser(@PathVariable String firstName, @PathVariable String lastName){
        return "Hello " .concat(firstName).concat(" ").concat(lastName);
    }
}
