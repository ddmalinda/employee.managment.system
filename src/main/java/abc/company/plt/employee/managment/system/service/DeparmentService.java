package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.dto.DeparmentDto;
import abc.company.plt.employee.managment.system.model.Deparment;
import abc.company.plt.employee.managment.system.repository.DeparmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeparmentService {
    @Autowired
    private DeparmentRepository deparmentRepository;

    private DeparmentDto convertToDTO(Deparment deparment) {
        DeparmentDto dto = new DeparmentDto();
        dto.setDeparmentID(deparment.getDeparmentID());
        dto.setDepartmentName(deparment.getDepartmentName());
        return dto;
    }

    //find department b{y ID
    public Optional<Deparment> getDeparmentById(Long departmentID){
        return deparmentRepository.findById(departmentID);
    }

    //Find ALL Department
    public List<DeparmentDto> getAllDeparments(){
        return deparmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect((Collectors.toList()));
    }

    //ADD new deparment
    public Deparment addDeparment(Deparment deparment){
        return deparmentRepository.save(deparment);
    }

    //update deparment
    public Deparment updateDepartment(Long deparmentID,Deparment deparmentDetails ){
        Deparment deparment = deparmentRepository.findById(deparmentID)
                .orElseThrow(()->new RuntimeException("Deparment not found by id :"+deparmentID));

        deparment.setDepartmentName(deparmentDetails.getDepartmentName());

        return deparmentRepository.save(deparment);
    }

    //delete deparment by id
    public void deletDeparment(Long id){
        deparmentRepository.deleteById(id);
    }
}
