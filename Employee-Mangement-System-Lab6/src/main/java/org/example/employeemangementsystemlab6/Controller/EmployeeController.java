package org.example.employeemangementsystemlab6.Controller;


import jakarta.validation.Valid;
import org.example.employeemangementsystemlab6.Api.ApiResponse;
import org.example.employeemangementsystemlab6.Model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    ArrayList <Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity<?> getEmployee(){
        if(!employees.isEmpty()){
            return ResponseEntity.status(200).body(employees);

        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no Employee"));
    }

    @PostMapping("/add")
        public  ResponseEntity<?> addEmployee(@RequestBody @Valid Employee employee, Errors errors){
                if(errors.hasErrors()){
                    String message = errors.getFieldError().getDefaultMessage();
                    return  ResponseEntity.status(400).body(message);
                }

                for(Employee e: employees){
                    if(e.getID().equalsIgnoreCase(employee.getID())){
                        return ResponseEntity.status(400).body(new ApiResponse("there is Employee with same id "));
                    }
                }

                employees.add(employee);
                return  ResponseEntity.status(200).body(new ApiResponse("the employee is added successfully"));

        }

        @PutMapping("/update/{id}")
        public ResponseEntity<?> updateEmployee(@PathVariable String id,@RequestBody @Valid Employee  employee,Errors errors){
               if(errors.hasErrors()){
                   String message = errors.getFieldError().getDefaultMessage();
                   return  ResponseEntity.status(400).body(message);
               }


                for (Employee e: employees){
                    if(e.getID().equalsIgnoreCase(id)){
                        employees.set(employees.indexOf(e),employee);
                        return ResponseEntity.status(200).body(new ApiResponse("The Employee with ID: "+ id +" updated successfully"));

                    }
                }
                    return ResponseEntity.status(400).body(new ApiResponse("The Employee not Found"));
        }
        @DeleteMapping("/delete/{id}")
        public  ResponseEntity<?> deleteEmployee(@PathVariable String id){
                for(Employee e: employees){
                    if(e.getID().equalsIgnoreCase(id)){
                        employees.remove(e);
                        return ResponseEntity.status(200).body(new ApiResponse("The Employee has been deleted successfully "));
                    }
                }
                return ResponseEntity.status(400).body(new ApiResponse("Employee Not Found"));
        }



@GetMapping("/get-by-position/{position}")
        public  ResponseEntity<?> searchByPosition(@PathVariable String position){
        ArrayList <Employee> supervisorEmployee = new ArrayList<>();
        ArrayList<Employee> coordinatorEmployee= new ArrayList<>();

        for(Employee e: employees){
            if(e.getPosition().equalsIgnoreCase(position)&&position.equalsIgnoreCase("supervisor")){
                supervisorEmployee.add(e);
            }
            if(e.getPosition().equalsIgnoreCase(position)&&position.equalsIgnoreCase("coordinator")){
                coordinatorEmployee.add(e);
            }
        }


        if(!supervisorEmployee.isEmpty()){
            return ResponseEntity.status(200).body(supervisorEmployee);
        }

            if(!coordinatorEmployee.isEmpty()){
                return  ResponseEntity.status(200).body(coordinatorEmployee);
            }
    return ResponseEntity.status(400).body(new ApiResponse("there are no employees right now with this position: "+position));

}
@GetMapping("/get-age-range/{min}/{max}")
public ResponseEntity<?> getEmployeeAgeRang(@PathVariable int min , @PathVariable int max){
        if(min<0 ||max<=min|| max<0){
            return  ResponseEntity.status(400).body(new ApiResponse("Invalid age"));
        }

        ArrayList<Employee> employeeRangAge= new ArrayList<>();

        for (Employee e: employees){
            if(e.getAge()>=min&&e.getAge()<=max){
                employeeRangAge.add(e);
            }
        }
    if(!employeeRangAge.isEmpty()){
        return ResponseEntity.status(200).body(employeeRangAge);
    }
        return ResponseEntity.status(400).body(new ApiResponse("there is no Employee with this range"));

}









@PutMapping("/apply-leave/{id}")
public ResponseEntity<?> applyAnnualLeave(@PathVariable String id){
        for (Employee e: employees){
            if (e.getID().equalsIgnoreCase(id)){
                if(e.isOnLeave()){
                    return  ResponseEntity.status(400).body(new ApiResponse("the Employee has leave currently "));
                }

                if(e.getAnnualLeave()<=0){
                    return ResponseEntity.status(400).body(new ApiResponse("there is no annual leave available for: "+e.getName()));

                }
                e.setOnLeave(true);
                e.setAnnualLeave(e.getAnnualLeave()-1);
                return ResponseEntity.status(200).body(new ApiResponse("apply the annual leave successfully and it has annual leave right now "+e.getAnnualLeave()));

            }

        }

        return ResponseEntity.status(400).body(new ApiResponse("Employee Not Found"));
}

@GetMapping("/get-no-leave/{id}")
public ResponseEntity<?> getEmployeeNoAnnualLeave(@PathVariable String id){
        ArrayList <Employee> employeesNoAnnualLeave= new ArrayList<>();

        for(Employee e: employees){
            if (e.getAnnualLeave()==0){
                employeesNoAnnualLeave.add(e);
            }
        }
        if(!employeesNoAnnualLeave.isEmpty()){
            return ResponseEntity.status(200).body(employeesNoAnnualLeave);

        }

        return ResponseEntity.status(400).body(new ApiResponse("employee not found"));
}


@PutMapping("/promote/{id}/{promoteEmpId}")
public ResponseEntity<?> promote(@PathVariable String id, @PathVariable String promoteEmpId){
        Employee requester= null;
        Employee promoteEmp= null;

        for (Employee e: employees){

                if (e.getPosition().equalsIgnoreCase("supervisor")&& e.getID().equalsIgnoreCase(id)){
                    requester=e;
                }
                if(e.getPosition().equalsIgnoreCase("coordinator")&& e.getID().equalsIgnoreCase(promoteEmpId)){
                    promoteEmp=e;
                }

        }



        if(requester==null){
            return ResponseEntity.status(400).body(new ApiResponse("requester should be supervisor "));
        }

        if(promoteEmp==null){
            return  ResponseEntity.status(400).body(new ApiResponse("Not Found"));
        }
        if(promoteEmp.getAge()<30){
            return ResponseEntity.status(400).body(new ApiResponse("The age should be greater than 30"));
        }

        if(promoteEmp.isOnLeave()){
            return ResponseEntity.status(400).body(new ApiResponse("the employee has leave currently "));
        }
        if(promoteEmp.getPosition().equalsIgnoreCase("supervisor")){
            return ResponseEntity.status(400).body(new ApiResponse("The employee is already supervisor"));
        }
        promoteEmp.setPosition("supervisor");
            return ResponseEntity.status(200).body(new ApiResponse("the employee has been promoted"));

}









}// end class
