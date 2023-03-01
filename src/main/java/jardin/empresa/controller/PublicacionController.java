package jardin.empresa.controller;

import jardin.empresa.DTO.PublicacionDTO;
import jardin.empresa.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @PostMapping()
    public ResponseEntity<PublicacionDTO> save(@RequestBody PublicacionDTO publicacionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.save(publicacionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO>get(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(publicacionService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PublicacionDTO>delete(@PathVariable Long id){
        publicacionService.delete(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO>put(@PathVariable Long id, @RequestBody PublicacionDTO publicacionDTO){
        return ResponseEntity.status(HttpStatus.OK).body(publicacionService.put(id, publicacionDTO));
    }

}
