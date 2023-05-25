package jardin.empresa.mapper;

import jardin.empresa.DTO.EmployeeDTO;
import jardin.empresa.model.Employee;
import jardin.empresa.repository.EmployeeRepository;
import jardin.empresa.service.impl.CloudinaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    public Employee dtoToEntity(EmployeeDTO employeeDTO, MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.upload(multipartFile);
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setLast_name(employeeDTO.getLast_name());
        employee.setTitle(employeeDTO.getTitle());
        employee.setBiography(employeeDTO.getBiography());
        employee.setImageId((String)result.get("public_id"));
        employee.setImageUrl((String)result.get("url"));
        return employee;
    }
    public EmployeeDTO entityToDto(Employee saved) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(saved.getId());
        employeeDTO.setName(saved.getName());
        employeeDTO.setLast_name(saved.getLast_name());
        employeeDTO.setTitle(saved.getTitle());
        employeeDTO.setBiography(saved.getBiography());
        employeeDTO.setImageUrl(saved.getImageUrl());
        employeeDTO.setDate_creation(saved.getDate_creation());
        return employeeDTO;
    }
    public Employee updateEntity(Long id, EmployeeDTO employeeDTO, MultipartFile multipartFile) throws IOException {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        if (multipartFile != null) {
            Map result = cloudinaryService.upload(multipartFile);
            Map delete = cloudinaryService.delete(employee.getImageId());
            employee.setImageId((String) result.get(("public_id")));
            employee.setImageUrl((String) result.get(("url")));
        }
        employee.setName(employeeDTO.getName());
        employee.setName(employeeDTO.getName());
        employee.setLast_name(employeeDTO.getLast_name());
        employee.setTitle(employeeDTO.getTitle());
        employee.setBiography(employeeDTO.getBiography());
        employee.setImageId(employee.getImageId());
        employee.setImageUrl(employee.getImageUrl());
        employee.setDate_creation(employeeDTO.getDate_creation());
        return employee;
    }
    public List<EmployeeDTO> listEntityDto(List<Employee> listEmployees) {
        return  listEmployees.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    
}
