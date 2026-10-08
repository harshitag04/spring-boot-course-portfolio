package org.example.departmentservice.controllers;

import org.example.departmentservice.dto.DepartmentDTO;
import org.example.departmentservice.exceptions.ResourceNotFoundException;
import org.example.departmentservice.services.DepartmentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/departments")
public class DepartmentControllers {

    private final DepartmentServices departmentServices;

    public DepartmentControllers(DepartmentServices departmentServices){
        this.departmentServices = departmentServices;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(@RequestParam (required = false, name = "id") Long Id,
                                                                 @RequestParam (required = false)LocalDate createdAt){
        return ok(departmentServices.getAllDepartments());
    }

    @GetMapping(path = "getDepartment/{departmentID}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable (name = "departmentID") Long id){
        return departmentServices.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
    }

    @PostMapping(path = "createDepartment")
    public ResponseEntity<DepartmentDTO> createDepartment(@Validated @RequestBody DepartmentDTO departmentDTO){
        return new ResponseEntity<>(departmentServices.createDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable (name = "departmentId") Long id,
                                          @RequestBody DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentServices.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping(path = "deleteDepartment/{departmentId}")
    public void deleteDepartmentById(@PathVariable (name = "departmentId") Long id){
        departmentServices.deleteDepartmentById(id);
    }

    @PatchMapping(path = "updateDepartmentPartially/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentPartiallyById(@PathVariable (name = "departmentId") Long id,
                                                       @RequestBody HashMap<String, Object> updates){
        DepartmentDTO updatedDepartment = departmentServices.updateDepartmentPartiallyById(id, updates);
        if(updatedDepartment == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(updatedDepartment);
    }
}
