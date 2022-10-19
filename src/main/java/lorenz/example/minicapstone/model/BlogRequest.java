package lorenz.example.minicapstone.model;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class BlogRequest {

    private String blogName;
    private String blogAuthor;
    private String description;
}
