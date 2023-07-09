package jardin.empresa.controller;

import jardin.empresa.DTO.CompanyDTO;
import jardin.empresa.service.CompanyService;
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
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CompanyDTO>create(
            @Valid @RequestPart(value = "data_company") CompanyDTO companyDTO,
            @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.save(companyDTO, multipartFile));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO>get(@Valid @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.get(Long.valueOf(id)));
    }
    @GetMapping("/all")
    public ResponseEntity<List<CompanyDTO>>findAll(){
        List<CompanyDTO> list = companyService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CompanyDTO>update(
            @Valid @PathVariable String id,
            @Valid @RequestPart(value = "data_company") CompanyDTO companyDTO,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.put(Long.valueOf(id), companyDTO, multipartFile));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDTO>delete(@Valid @PathVariable String id) throws IOException {
        companyService.delete(Long.valueOf(id));
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
