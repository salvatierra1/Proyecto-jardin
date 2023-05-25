package jardin.empresa.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {

    private Long id;

    private String imageId;

    private String name;

    private String location;

    private String resolution;

    private String biography;

    private String imageUrl;

    private String schedules;

    private String phone;

    private String email;

    private String linkIg;

    private String linkFb;

    private String linkLk;

    private String mission;

    private String vision;

    private LocalDate date_creation;
}
