package jardin.empresa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE company SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageId;

    private String imageCompanyId;

    private String name;

    private String location;

    private String resolution;

    @Column(name="biography", length=2000)
    private String biography;

    private String imageUrl;

    private String imageCompanyUrl;

    private String schedules;

    private String phone;

    private String email;

    private String linkIg;

    private String linkFb;

    private String linkLk;

    @Column(name="mission", length=2000)
    private String mission;

    @Column(length=2000)
    private String vision;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date_creation;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date_update;

    private boolean deleted = Boolean.FALSE;

}
