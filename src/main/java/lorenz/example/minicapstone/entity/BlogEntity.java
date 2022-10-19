package lorenz.example.minicapstone.entity;

import lombok.*;
import lorenz.example.minicapstone.config.SchemaConfiguration;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConfiguration.SCHEMA_NAME, name="BLOGS")
public class BlogEntity {

    @Id
    private UUID blogId;
    private String blogName;
    private String blogAuthor;
    private String imageLink;
    private String description;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;


}
