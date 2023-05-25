package jardin.empresa.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicationDTO {

    private Long id;

    private String imageId;

    private String title;

    private String imageUrl;


    private String biography;

    private boolean relevant;

    private LocalDate date_creation;

    private LocalDate date_update;

}
