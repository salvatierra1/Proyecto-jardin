package jardin.empresa.mapper;

import jardin.empresa.DTO.PublicationDTO;
import jardin.empresa.model.Publication;
import jardin.empresa.repository.PublicationRepository;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PublicationMapper {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private CloudinaryServiceImpl cloudinaryService;
    public Publication dtoToEntity(PublicationDTO publicationDTO, MultipartFile multipartFile) throws IOException {
        Publication publication = new Publication();
        Map result = cloudinaryService.upload(multipartFile);
        publication.setImageId((String)result.get("public_id"));
        publication.setImageUrl((String)result.get("url"));
        publication.setTitle(publicationDTO.getTitle());
        publication.setBiography(publicationDTO.getBiography());
        publication.setRelevant(publicationDTO.isRelevant());
        return publication;
    }
    public PublicationDTO entityToDto(Publication saved) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(saved.getId());
        publicationDTO.setImageUrl(saved.getImageUrl());
        publicationDTO.setTitle(saved.getTitle());
        publicationDTO.setBiography(saved.getBiography());
        publicationDTO.setRelevant(saved.isRelevant());
        publicationDTO.setDate_creation(saved.getDate_creation());
        return publicationDTO;
    }

    public Publication updateEntity(Long id, PublicationDTO publicationDTO, MultipartFile multipartFile) throws IOException {
        Publication publication = publicationRepository.findById(id).orElseThrow();
        if (multipartFile != null) {
            Map result = cloudinaryService.upload(multipartFile);
            Map delete = cloudinaryService.delete(publication.getImageId());
            publication.setImageId((String) result.get(("public_id")));
            publication.setImageUrl((String) result.get(("url")));
        }
        publication.setImageUrl(publication.getImageUrl());
        publication.setTitle(publication.getTitle());
        publication.setBiography(publicationDTO.getBiography());
        publication.setRelevant(publicationDTO.isRelevant());
        publication.setDate_creation(publicationDTO.getDate_creation());
        return publication;
    }

    public List<PublicationDTO> listEntityDto(List<Publication> listPublication) {
       return  listPublication.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<PublicationDTO> listEntityDtoRelevant(List<Publication> listPublication) {
        return  listPublication.stream().map(this::entityToDto).filter(a->a.isRelevant() == true).collect(Collectors.toList());
    }

}
