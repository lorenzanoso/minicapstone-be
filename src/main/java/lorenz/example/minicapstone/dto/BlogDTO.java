package lorenz.example.minicapstone.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class BlogDTO {

    private UUID blogID;
    private String blogName;
    private String blogAuthor;
    private String imageLink;
    private String description;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

}
