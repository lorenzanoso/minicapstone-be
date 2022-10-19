package lorenz.example.minicapstone.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductRequest {

    private String productName;
    private Float price;
    private Float ratings;
    private String type;
    private String filter;
    private String description;

}
