package lorenz.example.minicapstone.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoModel {
    private String firstName;
    private String lastName;
}
