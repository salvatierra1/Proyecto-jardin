package jardin.empresa.controller;
import jardin.empresa.DTO.ProjectDTO;
import jardin.empresa.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjectDTO>create(
            @Valid @RequestPart(value = "data_project") ProjectDTO projectDTO,
            @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.save(projectDTO, multipartFile));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO>get(@Valid @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.get(Long.valueOf(id)));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProjectDTO>>getAll(){
        List<ProjectDTO> list = projectService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjectDTO>update(
            @Valid @PathVariable String id,
            @Valid @RequestPart(value = "data_project") ProjectDTO projectDTO,
            @RequestPart(value = "image", required = false)MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.put(Long.valueOf(id), projectDTO, multipartFile));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO>delete(@Valid @PathVariable String id) throws IOException {
        projectService.delete(Long.valueOf(id));
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
