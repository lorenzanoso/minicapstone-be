package lorenz.example.minicapstone.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID productId;
    private String productName;
    private String imageLink;
    private Float price;
    private Float ratings;
    private String type;
    private String filter;
    private String description;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

}
