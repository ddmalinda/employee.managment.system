package abc.company.plt.employee.managment.system.service;

import abc.company.plt.employee.managment.system.model.Emplyee;
import abc.company.plt.employee.managment.system.repository.EmplyeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmplyeeService {

    @Autowired
    private EmplyeeRepository emplyeeRepository;

    // Find one employee by ID
    public Optional<Emplyee> getEmplyeebyId(Long emplyeeID){
        return emplyeeRepository.findById(emplyeeID);
    }

    // Find all employees
    public List<Emplyee> getAllEmplyeeList(){
        return emplyeeRepository.findAll();
    }

    // Delete an employee by ID
    public void deleteEmpyeeById(Long id){
        emplyeeRepository.deleteById(id);
    }

    // Add a new employee
    public Emplyee addEmplyee(Emplyee emplyee){
        return emplyeeRepository.save(emplyee);
    }

    // Update an existing employee
    public Emplyee updateEmplyee(Long emplyeeId, Emplyee emplyeeDetails){
        Emplyee emplyee = emplyeeRepository.findById(emplyeeId)
                .orElseThrow(() -> new RuntimeException("Emplyee not found with id: " + emplyeeId));

        // Update fields from the details provided
        emplyee.setEmail(emplyeeDetails.getEmail());
        emplyee.setPassword(emplyeeDetails.getPassword()); // Consider password handling
        emplyee.setFirstName(emplyeeDetails.getFirstName());
        emplyee.setLastName(emplyeeDetails.getLastName());
        emplyee.setDeparment(emplyeeDetails.getDeparment()); // Update associated department

        return emplyeeRepository.save(emplyee);
    }

    // Find employees belonging to a specific department
    public List<Emplyee> getEmplyeeListByDepartmentId(Long departmentId){
        return emplyeeRepository.findByDeparmentDeparmentID(departmentId);
    }
}