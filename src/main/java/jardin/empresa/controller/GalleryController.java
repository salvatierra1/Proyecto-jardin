package jardin.empresa.controller;

import jardin.empresa.DTO.GalleryDTO;
import jardin.empresa.model.Gallery;
import jardin.empresa.service.GalleryService;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/gallery")
@CrossOrigin(origins = "*")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<GalleryDTO>create(
            @Valid @RequestPart(value = "data_gallery") GalleryDTO galleryDTO,
            @RequestPart(value = "image") MultipartFile multipartFile
            ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.save(galleryDTO, multipartFile));
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Gallery>> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        Page<Gallery> galleries = galleryService.page(
                PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            galleries = galleryService.page(
                    PageRequest.of(page, size, Sort.by(order).descending()));
        return ResponseEntity.status(HttpStatus.OK).body(galleries);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable String id) throws IOException {
        galleryService.delete(Long.valueOf(id));
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
