package jardin.empresa.mapper;

import jardin.empresa.DTO.ProjectDTO;
import jardin.empresa.exception.GenericException;
import jardin.empresa.model.Project;
import jardin.empresa.repository.ProjectRepository;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ProjectMapper {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    public Project dtoToEntity(ProjectDTO projectDTO, MultipartFile multipartFile) throws IOException {
        Project project = new Project();
        Map result = cloudinaryService.upload(multipartFile);
        project.setName(projectDTO.getName());
        project.setBiography(projectDTO.getBiography());
        project.setImageId((String)result.get("public_id"));
        project.setImageUrl((String)result.get("url"));
        return project;
    }

    public ProjectDTO entityToDto(Project saved) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(saved.getId());
        projectDTO.setName(saved.getName());
        projectDTO.setBiography(saved.getBiography());
        projectDTO.setImageUrl(saved.getImageUrl());
        projectDTO.setDate_creation(saved.getDate_creation());
        projectDTO.setDate_update(saved.getDate_update());
        return projectDTO;
    }

    public Project updateEntity(Long id, ProjectDTO projectDTO, MultipartFile multipartFile) throws IOException {
        Project project = projectRepository.findById(id).orElseThrow();
        if (multipartFile != null) {
            Map result = cloudinaryService.upload(multipartFile);
            Map delete = cloudinaryService.delete(project.getImageId());
            project.setImageId((String) result.get(("public_id")));
            project.setImageUrl((String) result.get(("url")));
        }
            project.setName(projectDTO.getName());
            project.setBiography(projectDTO.getBiography());
            project.setImageUrl(project.getImageUrl());
            return project;
    }
    public List<ProjectDTO> listEntityDto(List<Project> projects) {
        return projects.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
