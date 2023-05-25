package jardin.empresa.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {

    private Long id;

    private String imageId;

    private String imageUrl;

    private String name;

    private String biography;

    private LocalDate date_creation;

    private LocalDate date_update;

}
