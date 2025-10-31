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
    private DepartmentRepository departmentRepository;

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentID(department.getDepartmentID());
        dto.setDepartmentName(department.getDepartmentName());
        return dto;
    }

    //find department b{y ID
    public Optional<Department> getDepartmentById(Long departmentID){
        return departmentRepository.findById(departmentID);
    }

    //Find ALL Department
    public List<DepartmentDTO> getAllDepartments(){
        return departmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect((Collectors.toList()));
    }

    //ADD new Department
    public Department addDepartment(Department department){
        return departmentRepository.save(department);
    }

    //update Department
    public Department updateDepartment(Long departmentID, Department departmentDetails){
        Department department = departmentRepository.findById(departmentID)
                .orElseThrow(()->new RuntimeException("Department not found by id :"+departmentID));

        department.setDepartmentName(departmentDetails.getDepartmentName());

        return departmentRepository.save(department);
    }

    //delete Department by id
    public void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }
}
