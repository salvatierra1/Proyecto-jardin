package jardin.empresa.mapper;

import jardin.empresa.DTO.CompanyDTO;
import jardin.empresa.model.Company;
import jardin.empresa.repository.CompanyRepository;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    public Company dtoToEntity(CompanyDTO companyDTO, MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.upload(multipartFile);
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setBiography(companyDTO.getBiography());
        company.setResolution(companyDTO.getResolution());
        company.setLocation(companyDTO.getLocation());
        company.setSchedules(companyDTO.getSchedules());
        company.setPhone(companyDTO.getPhone());
        company.setEmail(companyDTO.getEmail());
        company.setImageId((String)result.get("public_id"));
        company.setImageUrl((String)result.get("url"));
        company.setLinkIg(companyDTO.getLinkIg());
        company.setLinkFb(companyDTO.getLinkFb());
        company.setLinkLk(companyDTO.getLinkLk());
        company.setMission(companyDTO.getMission());
        company.setVision(companyDTO.getVision());
        return company;
    }
    public CompanyDTO entityToDto(Company saved) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setBiography(saved.getBiography());
        dto.setResolution(saved.getResolution());
        dto.setLocation(saved.getLocation());
        dto.setSchedules(saved.getSchedules());
        dto.setPhone(saved.getPhone());
        dto.setEmail(saved.getEmail());
        dto.setImageUrl(saved.getImageUrl());
        dto.setLinkIg(saved.getLinkIg());
        dto.setLinkFb(saved.getLinkFb());
        dto.setLinkLk(saved.getLinkLk());
        dto.setMission(saved.getMission());
        dto.setVision(saved.getVision());
        return dto;
    }

    public Company updateEntity(Long id, CompanyDTO companyDTO, MultipartFile multipartFile) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        Map delete = cloudinaryService.delete(company.getImageId());
        Map result = cloudinaryService.upload(multipartFile);
        company.setName(companyDTO.getName());
        company.setBiography(companyDTO.getBiography());
        company.setResolution(companyDTO.getResolution());
        company.setLocation(companyDTO.getLocation());
        company.setSchedules(companyDTO.getSchedules());
        company.setPhone(companyDTO.getPhone());
        company.setEmail(companyDTO.getEmail());
        company.setImageId((String)result.get("public_id"));
        company.setImageUrl((String)result.get("url"));
        company.setLinkIg(companyDTO.getLinkIg());
        company.setLinkFb(companyDTO.getLinkFb());
        company.setLinkLk(companyDTO.getLinkLk());
        company.setMission(companyDTO.getMission());
        company.setVision(companyDTO.getVision());
        return company;
    }
    public List<CompanyDTO> listEntityDto(List<Company> listCompany) {
        return  listCompany.stream().map(this::entityToDto).collect(Collectors.toList());

    }
}
