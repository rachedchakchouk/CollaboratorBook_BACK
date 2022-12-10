package com.ditriot.service;

import com.ditriot.dto.ReclamationRequestDto;
import com.ditriot.dto.ReclamationResponseDto;

import java.util.List;

public interface ReclamationService {
    ReclamationResponseDto getReclamationById(Long id);
    List<ReclamationResponseDto>getAll();
    List<ReclamationResponseDto> getBySource(Long senderId);
    List<ReclamationResponseDto> getActiveBySource(Long senderId);
    List<ReclamationResponseDto> getArchivedBySource(Long senderId);

    List<ReclamationResponseDto> getByDestination(Long receiverId);
    List<ReclamationResponseDto> getActiveByDestination(Long receiverId);
    List<ReclamationResponseDto> getArchivedByDestination(Long receiverId);
    ReclamationResponseDto addReclamation(ReclamationRequestDto reclamationRequestDto);
    ReclamationResponseDto updateReclamation(ReclamationRequestDto reclamationRequestDto , Long id);
    void archiveReclamation(Long id);
    void noarchiveReclamation(Long id);

    void deleteReclamation(Long id);
    void reclamationToSender(Long reclamationId,Long senderId);
    void reclamationToReceiver(Long reclamationId,Long receiverId);
}
