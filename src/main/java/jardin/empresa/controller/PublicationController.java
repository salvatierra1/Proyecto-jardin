package jardin.empresa.controller;

import jardin.empresa.DTO.PublicationDTO;
import jardin.empresa.service.PublicationService;
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
@RequestMapping("/publication")
@CrossOrigin(origins = "*")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PublicationDTO>create(
            @Valid @RequestPart(value = "data_publication")PublicationDTO publicationDTO,
            @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.save(publicationDTO, multipartFile));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO>get(@Valid @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(publicationService.get(Long.valueOf(id)));
    }
    @GetMapping("/all")
    public ResponseEntity<List<PublicationDTO>>getAll(){
        List<PublicationDTO> list = publicationService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/relevant")
    public ResponseEntity<List<PublicationDTO>> getAllRelevant(){
        List<PublicationDTO> list = publicationService.getListRelevant();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PublicationDTO>update(
            @Valid @PathVariable String id,
            @Valid @RequestPart(value = "data_publication")PublicationDTO publicationDTO,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(publicationService.put(Long.valueOf(id), publicationDTO, multipartFile));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PublicationDTO>delete(@Valid @PathVariable String id) throws IOException {
        publicationService.delete(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
