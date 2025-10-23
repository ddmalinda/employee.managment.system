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

 public Optional<Emplyee> getEmplyeebyId(Long emplyeeID){
     return emplyeeRepository.findById(emplyeeID);
 }

 public List<Emplyee> getAllEmplyeeList(){
     return emplyeeRepository.findAll();
 }

 public void deleteEmpyeeById(Long id){
     emplyeeRepository.deleteById(id);
 }
 public Emplyee addEmplyee(Emplyee emplyee){
     return emplyeeRepository.save(emplyee);
 }
 public Emplyee updateEmplyee(Long emplyeeId,Emplyee emplyeeDetails){
     Emplyee emplyee = emplyeeRepository.findById(emplyeeId)
     .orElseThrow(() -> new RuntimeException("Emplyee not found with id: " + emplyeeId));

     // copy updatable fields
     emplyee.setEmail(emplyeeDetails.getEmail());
     emplyee.setPassword(emplyeeDetails.getPassword());
     emplyee.setFirstName(emplyeeDetails.getFirstName());
     emplyee.setLastName(emplyeeDetails.getLastName());
     emplyee.setDeparment(emplyeeDetails.getDeparment());

     return emplyeeRepository.save(emplyee);
 }
 public List<Emplyee> getEmplyeeListByDepartmentId(Long departmentId){
    return emplyeeRepository.findbyDepartmentId(departmentId);
 }
}
