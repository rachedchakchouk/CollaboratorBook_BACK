package com.detiriot.businessms.service;

import com.detiriot.businessms.dto.DepartmentRequestDto;
import com.detiriot.businessms.dto.DepartmentResponseDto;
import com.detiriot.businessms.dto.OfficeResponseDto;
import com.detiriot.businessms.mapper.DepartmentMapper;
import com.detiriot.businessms.mapper.OfficeMapper;
import com.detiriot.businessms.model.Department;
import com.detiriot.businessms.model.Office;
import com.detiriot.businessms.repo.DepartmentRepo;
import com.detiriot.businessms.repo.OfficeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    final DepartmentRepo departmentRepo;
    final DepartmentMapper departmentMapper;
    final OfficeMapper officeMapper;
    final OfficeRepo officeRepo;

    public DepartmentServiceImpl(DepartmentRepo departmentRepo, DepartmentMapper departmentMapper, OfficeRepo officeRepo , OfficeMapper officeMapper) {
        this.departmentRepo = departmentRepo;
        this.departmentMapper = departmentMapper;
        this.officeRepo = officeRepo;
        this.officeMapper = officeMapper;
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepo.findById(id).get();
        Office office=department.getOffice();
        DepartmentResponseDto departmentResponseDto=departmentMapper.departmentToDepartmentResponseDto(department);
        departmentResponseDto.setOffice(officeMapper.officeToOfficeResponseDto(office));
        return departmentResponseDto;
    }

    @Override
    public List<DepartmentResponseDto> getAll() {
        List<Department> departments = departmentRepo.findAll();
        List<DepartmentResponseDto> departmentResponseDtos = departments.stream().map(department -> departmentMapper.departmentToDepartmentResponseDto(department)).collect(Collectors.toList());

        return departmentResponseDtos;
    }

    @Override
    public List<DepartmentResponseDto> getByOffice(Long id) {
        Office office = officeRepo.findById(id).get();
        List<Department> departments = departmentRepo.findByOffice(office);
        List<DepartmentResponseDto> departmentResponseDtos = departments.stream().map(department -> departmentMapper.departmentToDepartmentResponseDto(department)).collect(Collectors.toList());
        return departmentResponseDtos;
    }

    @Override
    public List<DepartmentResponseDto> getActiveByOffice(Long id) {
        Office office = officeRepo.findById(id).get();
        List<Department> departments = departmentRepo.findDepartmentsByOfficeAndArchived(office, false);
        List<DepartmentResponseDto> departmentResponseDtos = departments.stream().map(department -> departmentMapper.departmentToDepartmentResponseDto(department)).collect(Collectors.toList());
        return departmentResponseDtos;
    }


    @Override
    public List<DepartmentResponseDto> getArchivedByOffice(Long id) {
        Office office = officeRepo.findById(id).get();
        List<Department> departments = departmentRepo.findDepartmentsByOfficeAndArchived(office, true);
        List<DepartmentResponseDto> departmentResponseDtos = departments.stream().map(department -> departmentMapper.departmentToDepartmentResponseDto(department)).collect(Collectors.toList());
        return departmentResponseDtos;
    }

    @Override
    public DepartmentResponseDto addDepartment(DepartmentRequestDto departmentRequestDto) {
        Department department = departmentMapper.departmentRequestDtoToDepartment(departmentRequestDto);
        department.setArchived(false);
        Department addDepartment = departmentRepo.save(department);
        DepartmentResponseDto departmentResponseDto = departmentMapper.departmentToDepartmentResponseDto(addDepartment);
        return departmentResponseDto;

    }

    @Override
    public DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto, Long id) {
        Department department = departmentRepo.findById(id).get();
        Department updateDepartment = departmentMapper.departmentRequestDtoToDepartment(departmentRequestDto);
        department = departmentRepo.save(updateDepartment);

        return departmentMapper.departmentToDepartmentResponseDto(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).get();
        departmentRepo.delete(department);
    }


    @Override
    public void assignmentDepartmentToOffice(Long dId, Long oId) {
        Office office = officeRepo.findById(oId).get();
        Department department = departmentRepo.findById(dId).get();
        department.setOffice(office);
        departmentRepo.save(department);

    }

    @Override
    public void archiveDepartment(Long departmentId) {
        Department department=departmentRepo.findById(departmentId).get();
        department.setArchived(true);
        departmentRepo.save(department);

    }

    @Override
    public OfficeResponseDto getOfficeByDepId(Long depId) {
        Department department=departmentRepo.findById(depId).get();
        Office office=department.getOffice();
        OfficeResponseDto officeResponseDto=officeMapper.officeToOfficeResponseDto(office);
        return officeResponseDto;
    }
}
