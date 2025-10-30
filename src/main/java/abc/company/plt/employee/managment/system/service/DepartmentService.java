package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.dto.DepartmentDTO;
import abc.company.plt.employee.managment.system.model.Department;
import abc.company.plt.employee.managment.system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository DepartmentRepository;

    private DepartmentDTO convertToDTO(Department Department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentID(Department.getDepartmentID());
        dto.setDepartmentName(Department.getDepartmentName());
        return dto;
    }

    //find department b{y ID
    public Optional<Department> getDepartmentById(Long departmentID){
        return DepartmentRepository.findById(departmentID);
    }

    //Find ALL Department
    public List<DepartmentDTO> getAllDepartments(){
        return DepartmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect((Collectors.toList()));
    }

    //ADD new Department
    public Department addDepartment(Department Department){
        return DepartmentRepository.save(Department);
    }

    //update Department
    public Department updateDepartment(Long DepartmentID,Department DepartmentDetails ){
        Department Department = DepartmentRepository.findById(DepartmentID)
                .orElseThrow(()->new RuntimeException("Department not found by id :"+DepartmentID));

        Department.setDepartmentName(DepartmentDetails.getDepartmentName());

        return DepartmentRepository.save(Department);
    }

    //delete Department by id
    public void deletDepartment(Long id){
        DepartmentRepository.deleteById(id);
    }
}
