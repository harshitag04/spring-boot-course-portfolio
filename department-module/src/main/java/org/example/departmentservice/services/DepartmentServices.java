package org.example.departmentservice.services;

import org.example.departmentservice.dto.DepartmentDTO;
import org.example.departmentservice.entities.Department;
import org.example.departmentservice.exceptions.ResourceNotFoundException;
import org.example.departmentservice.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServices {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServices(DepartmentRepository departmentRepository, ModelMapper modelMapper){
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments =  departmentRepository.findAll();
        return departments
                .stream()
                .map(DepartmentEntity -> modelMapper.map(DepartmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department insertEntity = modelMapper.map(departmentDTO, Department.class);
        Department savedDepartment = departmentRepository.save(insertEntity);
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id).
                map((department -> modelMapper.map(department, DepartmentDTO.class)));
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department departmentEntity = modelMapper.map(departmentDTO, Department.class);
        departmentEntity.setId(id);
        Department updatedDepartment = departmentRepository.save(departmentEntity);
        return modelMapper.map(updatedDepartment, DepartmentDTO.class);
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    public DepartmentDTO updateDepartmentPartiallyById(Long id, HashMap<String, Object> updates) {
        Department existingEntity = getDepartmentById(id)
                .map((existing) -> modelMapper.map(existing, Department.class))
                .orElse(null);
        if(existingEntity == null){
            throw  new ResourceNotFoundException("Resource Not Found" + id);
        }
        updates.forEach((key, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(Department.class, key);
            if (fieldToBeUpdated != null) {
                ReflectionUtils.makeAccessible(fieldToBeUpdated);
                ReflectionUtils.setField(fieldToBeUpdated, existingEntity, value);
            }
        });
        Department updatedDepartment = departmentRepository.save(existingEntity);
        return modelMapper.map(updatedDepartment, DepartmentDTO.class);
    }
}
