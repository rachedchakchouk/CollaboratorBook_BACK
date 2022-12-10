package com.ditriot.controller;

import com.ditriot.dto.ReclamationRequestDto;
import com.ditriot.dto.ReclamationResponseDto;
import com.ditriot.mapper.ReclamationMapper;
import com.ditriot.service.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/Reclamations")
public class ReclamationController {
    final ReclamationService reclamationService;
    final ReclamationMapper reclamationMapper;

    @GetMapping
    @ResponseBody
    public List<ReclamationResponseDto> getReclamation() {
        return reclamationService.getAll();
    }

    @GetMapping("/getbyId/{id}")
    @ResponseBody
    public ReclamationResponseDto findbyid(@PathVariable Long id) {
        return reclamationService.getReclamationById(id);
    }

    @GetMapping("/getbysource/{senderId}")
    @ResponseBody
    public List<ReclamationResponseDto> getBySource(@PathVariable Long senderId) {
        return reclamationService.getBySource(senderId);
    }

    @GetMapping("/active/getbysource/{senderId}")
    @ResponseBody
    public List<ReclamationResponseDto> getActiveBySource(@PathVariable Long senderId) {
        return reclamationService.getActiveBySource(senderId);
    }

    @GetMapping("/archived/getbysource/{senderId}")
    @ResponseBody
    public List<ReclamationResponseDto> getArchivedBySource(@PathVariable Long senderId) {
        return reclamationService.getArchivedBySource(senderId);
    }

    @GetMapping("/getbyDestination/{receiverId}")
    @ResponseBody
    public List<ReclamationResponseDto> getByDestination(@PathVariable Long receiverId) {
        return reclamationService.getByDestination(receiverId);
    }

    @GetMapping("/active/getbyDestination/{receiverId}")
    @ResponseBody
    public List<ReclamationResponseDto> getActiveByDestination(@PathVariable Long receiverId) {
        return reclamationService.getActiveByDestination(receiverId);
    }

    @GetMapping("/archived/getbyDestination/{receiverId}")
    @ResponseBody
    public List<ReclamationResponseDto> getArchivedByDestination(@PathVariable Long receiverId) {
        return reclamationService.getArchivedByDestination(receiverId);
    }

    @PostMapping("/newReclamation")
    public ReclamationResponseDto newReclamation(@RequestBody ReclamationRequestDto reclamationRequestDto) {
        return reclamationService.addReclamation(reclamationRequestDto);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ReclamationResponseDto updateReclamation(@RequestBody ReclamationRequestDto reclamationRequestDto, @PathVariable Long id) {
        return reclamationService.updateReclamation(reclamationRequestDto, id);
    }

    @DeleteMapping("/archive/{id}")
    public void archiveReclamation(@PathVariable Long id) {
        reclamationService.archiveReclamation(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveReclamation(@PathVariable Long id) {
        reclamationService.noarchiveReclamation(id);
    }
    @PutMapping("/reclamationToSender/{reclamationId}/{senderId}")
    public void reclamationToSender(@PathVariable Long reclamationId, @PathVariable Long senderId) {
        reclamationService.reclamationToSender(reclamationId, senderId);
    }

    @PutMapping("/reclamationToReceiver/{reclamationId}/{receiverId}")
    public void reclamationToReceiver(@PathVariable Long reclamationId, @PathVariable Long receiverId) {
        reclamationService.reclamationToReceiver(reclamationId, receiverId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }


}
