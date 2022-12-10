package com.ditriot.service;

import com.ditriot.dto.ReclamationRequestDto;
import com.ditriot.dto.ReclamationResponseDto;
import com.ditriot.mapper.ReclamationMapper;
import com.ditriot.model.Employee;
import com.ditriot.model.Reclamation;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.ReclamationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReclamationServiceImpl implements ReclamationService {
    final ReclamationRepo reclamationRepo;
    final ReclamationMapper reclamationMapper;
    final EmployeeRepo employeeRepo;
    private ReclamationServiceImpl (ReclamationRepo reclamationRepo , ReclamationMapper reclamationMapper,EmployeeRepo employeeRepo){
        this.reclamationRepo = reclamationRepo;
        this.reclamationMapper = reclamationMapper;
        this.employeeRepo =employeeRepo;
    }

    @Override
    public ReclamationResponseDto getReclamationById(Long id) {
        Reclamation reclamation = reclamationRepo.findById(id).get();
        return reclamationMapper.reclamationToReclamationResponseDto(reclamation);
    }

    @Override
    public List<ReclamationResponseDto> getAll() {
        List<Reclamation> reclamations = reclamationRepo.findAll();
        List<ReclamationResponseDto>reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public List<ReclamationResponseDto> getBySource(Long senderId) {
        Employee employee=employeeRepo.findById(senderId).get();
        List<Reclamation> reclamations=reclamationRepo.findBySource(employee);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public List<ReclamationResponseDto> getActiveBySource(Long senderId) {
        Employee employee=employeeRepo.findById(senderId).get();
        List<Reclamation> reclamations=reclamationRepo.findReclamationsBySourceAndArchived(employee,false);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public List<ReclamationResponseDto> getArchivedBySource(Long senderId) {
        Employee employee=employeeRepo.findById(senderId).get();
        List<Reclamation> reclamations=reclamationRepo.findReclamationsBySourceAndArchived(employee,true);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public List<ReclamationResponseDto> getByDestination(Long receiverId) {
        Employee employee=employeeRepo.findById(receiverId).get();
        List<Reclamation> reclamations=reclamationRepo.findByDestination(employee);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public List<ReclamationResponseDto> getActiveByDestination(Long receiverId) {
        Employee employee=employeeRepo.findById(receiverId).get();
        List<Reclamation> reclamations=reclamationRepo.findReclamationsByDestinationAndArchived(employee,false);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;     }

    @Override
    public List<ReclamationResponseDto> getArchivedByDestination(Long receiverId) {
        Employee employee=employeeRepo.findById(receiverId).get();
        List<Reclamation> reclamations=reclamationRepo.findReclamationsByDestinationAndArchived(employee,true);
        List<ReclamationResponseDto> reclamationResponseDtos=reclamations.stream().map(reclamation -> reclamationMapper.reclamationToReclamationResponseDto(reclamation)).collect(Collectors.toList());
        return reclamationResponseDtos;
    }

    @Override
    public ReclamationResponseDto addReclamation(ReclamationRequestDto reclamationRequestDto) {
        Reclamation reclamation = reclamationMapper.reclamationRequestDtoToReclamation(reclamationRequestDto);
        reclamation.setArchived(false);
        Reclamation newReclamation = reclamationRepo .save(reclamation);
        ReclamationResponseDto reclamationResponseDto = reclamationMapper.reclamationToReclamationResponseDto(newReclamation);
                return reclamationResponseDto;
    }

    @Override
    public ReclamationResponseDto updateReclamation(ReclamationRequestDto reclamationRequestDto, Long id) {
        Reclamation reclamation=reclamationRepo.findById(id).get();
        reclamationRequestDto.setId(reclamation.getId());
        Reclamation updatedreclamation=reclamationMapper.reclamationRequestDtoToReclamation(reclamationRequestDto);
        reclamationRepo.save(updatedreclamation);
        return reclamationMapper.reclamationToReclamationResponseDto(updatedreclamation);
    }

    @Override
    public void archiveReclamation(Long id) {
        Reclamation reclamation=reclamationRepo.findById(id).get();
        reclamation.setArchived(true);
        reclamationRepo.save(reclamation);

    }

    @Override
    public void noarchiveReclamation(Long id) {
        Reclamation reclamation=reclamationRepo.findById(id).get();
        reclamation.setArchived(false);
        reclamationRepo.save(reclamation);

    }

    @Override
    public void deleteReclamation(Long id) {
        Reclamation reclamation=reclamationRepo.findById(id).get();
        reclamationRepo.delete(reclamation);

    }

    @Override
    public void reclamationToSender(Long reclamationId, Long senderId) {
        Reclamation reclamation=reclamationRepo.findById(reclamationId).get();
        Employee sender=employeeRepo.findById(senderId).get();
        reclamation.setSource(sender);
        reclamationRepo.save(reclamation);

    }

    @Override
    public void reclamationToReceiver(Long reclamationId, Long receiverId) {
        Reclamation reclamation=reclamationRepo.findById(reclamationId).get();
        Employee receiver=employeeRepo.findById(receiverId).get();
        reclamation.setDestination(receiver);
        reclamationRepo.save(reclamation);

    }
}
