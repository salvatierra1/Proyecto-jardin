package jardin.empresa.mapper;
import jardin.empresa.DTO.GalleryDTO;
import jardin.empresa.model.Gallery;
import jardin.empresa.repository.GalleryRepository;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Component
public class GalleryMapper {

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    @Autowired
    private GalleryRepository galleryRepository;

    public Gallery dtoToEntity(GalleryDTO galleryDTO, MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.upload(multipartFile);
        Gallery gallery = new Gallery();
        gallery.setName((String)result.get("original_filename"));
        gallery.setImageId((String)result.get("public_id"));
        gallery.setImageUrl((String)result.get("url"));
        gallery.setDescription(galleryDTO.getDescription());
        gallery.setAlternative(galleryDTO.getAlternative());
        return gallery;
    }
    public GalleryDTO entityToDto(Gallery gallery) {
        GalleryDTO galleryDTO = new GalleryDTO();
        galleryDTO.setId(gallery.getId());
        galleryDTO.setImageUrl(gallery.getImageUrl());
        galleryDTO.setDescription(gallery.getDescription());
        galleryDTO.setAlternative(gallery.getAlternative());
        galleryDTO.setDate_creation(gallery.getDate_creation());
        return galleryDTO;
    }

}
