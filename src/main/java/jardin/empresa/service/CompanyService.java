package jardin.empresa.service;

import jardin.empresa.DTO.CompanyDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface CompanyService {
    CompanyDTO save(CompanyDTO companyDTO, MultipartFile multipartFile, MultipartFile multipartFileCompany) throws IOException;
    CompanyDTO get(Long id);
    void delete(Long id) throws IOException;
    CompanyDTO put(Long id, CompanyDTO companyDTO, MultipartFile multipartFile, MultipartFile multipartFileCompany) throws IOException;
    List<CompanyDTO> getList();
}
