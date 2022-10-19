package lorenz.example.minicapstone.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lorenz.example.minicapstone.dto.UserDTO;
import lorenz.example.minicapstone.entity.UserEntity;
import lorenz.example.minicapstone.exception.UserAlreadyExist;
import lorenz.example.minicapstone.model.UserRequest;
import lorenz.example.minicapstone.repository.UserRepository;
import lorenz.example.minicapstone.util.DateTimeUtil;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final DateTimeUtil dateTimeUtil;
    private final ModelMapper modelMapper;
    public UserDTO saveUser(@NonNull UserRequest newUser) {
        //check if email exist
        if (userRepository.findByEmail(newUser.getEmail()) != null) {

            throw new UserAlreadyExist("Email already in used");
        }

        //Initialize user
        UserEntity user = UserEntity.builder()
                .userId(UUID.randomUUID())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .totalOrders(0)
                .successOrders(0)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //Save to Database
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public String deleteUser(String email) {
        String response = "No Data has been deleted!";

        //Get User
        UserEntity user = userRepository.findByEmail(email);

        //check if user exist
        if (user != null) {
            userRepository.deleteByEmail(user.getEmail());
            response = email + "has been successfully deleted!";
        }
        return response;
    }
    public UserDTO updateUser(String oldEmail, UserRequest userRequest){
        //Initialize user
        UserEntity user = userRepository.findByEmail(oldEmail);

        //check if email is existing
        if(user == null) throw new IllegalStateException("User does not exist!");

        //update User
        UserEntity updatedUser = UserEntity
                .builder()
                .userId(user.getUserId())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .totalOrders(user.getTotalOrders())
                .successOrders(user.getSuccessOrders())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();
        // save user
        if(userRepository.findByEmail(updatedUser.getEmail()) != null){
            throw new UserAlreadyExist("Email already in used");
        }
        userRepository.save(updatedUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO loginUser(@NonNull UserRequest activeUser){
        //initialize user
        UserEntity user = userRepository.findByEmail(activeUser.getEmail());

        //check if user is existing
        if(user == null ) throw new UserAlreadyExist("User does not exist!");

        //check if email existing
        if(!Objects.equals(user.getPassword(), activeUser.getPassword())) throw new UserAlreadyExist("Invalid Password");

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO loginByProvider(@NonNull String email) {
        // Initialize User
        UserEntity user = userRepository.findByEmail(email);

        // Check if email is existing
        if (user == null) {
            // Initialize new user
            UserEntity newUser = UserEntity
                    .builder()
                    .userId(UUID.randomUUID())
                    .email(email)
                    .password(null)
                    .totalOrders(0)
                    .successOrders(0)
                    .createdDate(dateTimeUtil.currentDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build();

            // Save to database
            userRepository.save(newUser);

            return modelMapper.map(newUser, UserDTO.class);
        }

        return modelMapper.map(user, UserDTO.class);
    }
}