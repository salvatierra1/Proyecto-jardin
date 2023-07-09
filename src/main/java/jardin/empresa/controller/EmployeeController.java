package jardin.empresa.controller;
import jardin.empresa.DTO.EmployeeDTO;
import jardin.empresa.service.EmployeeService;
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
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeDTO>create(
            @Valid @RequestPart(value = "data_employee")EmployeeDTO employeeDTO,
            @RequestPart(value = "image")MultipartFile multipartFile
            ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeDTO, multipartFile));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO>get(@Valid @PathVariable String id) {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.get(Long.valueOf(id)));
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>>findAll(){
        List<EmployeeDTO> list = employeeService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeDTO>update(
            @Valid @PathVariable String id,
            @Valid @RequestPart(value = "data_employee")EmployeeDTO employeeDTO,
            @Valid @RequestPart(value = "image", required = false)MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.put(Long.valueOf(id), employeeDTO, multipartFile));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@Valid @PathVariable String id) throws IOException {
        employeeService.delete(Long.valueOf(id));
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
