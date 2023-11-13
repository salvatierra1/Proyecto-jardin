package jardin.empresa.service.impl;

import jardin.empresa.DTO.CompanyDTO;
import jardin.empresa.exception.GenericException;
import jardin.empresa.mapper.CompanyMapper;
import jardin.empresa.model.Company;
import jardin.empresa.repository.CompanyRepository;
import jardin.empresa.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    @Override
    public CompanyDTO save(CompanyDTO companyDTO, MultipartFile multipartFile, MultipartFile multipartFileCompany ) throws IOException {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null){
                throw new GenericException("Image no acceptable", HttpStatus.NOT_ACCEPTABLE);
            }
            Company company = companyMapper.dtoToEntity(companyDTO, multipartFile, multipartFileCompany);
            Company saved = companyRepository.save(company);
        return companyMapper.entityToDto(saved);
    }
    @Override
    public CompanyDTO get(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return companyMapper.entityToDto(company);
    }
    @Override
    public void delete(Long id) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        Map result = cloudinaryService.delete(company.getImageId());
        Map imageCompany = cloudinaryService.delete(company.getImageCompanyId());
        companyRepository.delete(company);
    }

    @Override
    public CompanyDTO put(Long id, CompanyDTO companyDTO, MultipartFile multipartFile, MultipartFile multipartFileCompany ) throws IOException {
        Company company = companyMapper.updateEntity(id, companyDTO, multipartFile, multipartFileCompany);
        Company saved = companyRepository.save(company);
        return companyMapper.entityToDto(saved);
    }

    @Override
    public List<CompanyDTO> getList() {
        List<Company> list = companyRepository.findAll();
        return companyMapper.listEntityDto(list);
    }
}
