package com.ditriot.service;

import com.ditriot.dto.DocumentRequestDto;
import com.ditriot.dto.DocumentResponseDto;
import com.ditriot.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentResponseDto addDocument (DocumentRequestDto documentRequestDto);
    List<DocumentResponseDto> getAll();
    DocumentResponseDto getDocumentById (Long id);
    List<DocumentResponseDto> getDocumentsByEmployee(Long idE);
    List<DocumentResponseDto> getActiveDocumentsByEmployee(Long idE);
    List<DocumentResponseDto> getArchivedDocumentsByEmployee(Long idE);
    List<DocumentResponseDto> getActivePorfilImgByEmployee(Long id);
    List<DocumentResponseDto> getArchivedPorfilImgByEmployee(Long id);
    List<DocumentResponseDto> archiveDocumentsWithEmployee(Long idE);
    void documentToEmployee(Long documentId,Long employeeId);
    void documentToPost(Long documentId,Long postId);
    void archivedocument(Long id);
    void noarchivedocument(Long id);

    void updateDocument(DocumentRequestDto documentRequestDto,Long id);
    void deleteDocument(Long id);

    Document saveDocument(Long id,MultipartFile file) throws Exception;
}
