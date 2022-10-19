package lorenz.example.minicapstone.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lorenz.example.minicapstone.dto.UserDTO;
import lorenz.example.minicapstone.model.UserRequest;
import lorenz.example.minicapstone.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String HelloWorld(){
        return "Hello World!";
    }

    @PutMapping("/signup")
    public UserDTO registerUser(@RequestBody @NonNull UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email){
        return userService.deleteUser(email);
    }

    @PostMapping("/update/{oldEmail}")
    public UserDTO updateUser(@PathVariable String oldEmail, @RequestBody UserRequest userRequest){
        return userService.updateUser(oldEmail, userRequest);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody @NonNull UserRequest userRequest){
        return userService.loginUser(userRequest);

    }

    @PostMapping("/loginByProvider/{email}")
    public UserDTO loginByProvider(@PathVariable String email) {
        return userService.loginByProvider(email);
    }

}
