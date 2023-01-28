package com.ditriot.controller;

import com.ditriot.dto.DocumentRequestDto;
import com.ditriot.dto.DocumentResponseDto;
import com.ditriot.mapper.DocumentMapper;
import com.ditriot.model.Document;
import com.ditriot.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/documents")
public class DocumentController {

    final DocumentService documentService;
    final DocumentMapper documentMapper;


    @GetMapping
    @ResponseBody
    public List<DocumentResponseDto> getDocuments() {
        return documentService.getAll();
    }

    @GetMapping("/documentbyid/{id}")
    @ResponseBody
    public DocumentResponseDto findbyId(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping("/byemployee/{id}")
    @ResponseBody
    public List<DocumentResponseDto> getDocumentsByEmployee(@PathVariable Long id) {
        return documentService.getDocumentsByEmployee(id);
    }
    @GetMapping("/activeproimg/byemployee/{id}")
    @ResponseBody
    public List<DocumentResponseDto> getaciveProfileimgByEmployee(@PathVariable Long id) {
        return documentService.getActivePorfilImgByEmployee(id);
    }
    @GetMapping("/archivedproimg/byemployee/{id}")
    @ResponseBody
    public List<DocumentResponseDto> getarchivedProfileimgByEmployee(@PathVariable Long id) {
        return documentService.getArchivedPorfilImgByEmployee(id);
    }

    @GetMapping("/active/byemployee/{id}")
    @ResponseBody
    public List<DocumentResponseDto> getActiveDocumentsByEmployee(@PathVariable Long id) {
        return documentService.getActiveDocumentsByEmployee(id);
    }

    @GetMapping("/archived/byemployee/{id}")
    @ResponseBody
    public List<DocumentResponseDto> getArchivedDocumentsByEmployee(@PathVariable Long id) {
        return documentService.getArchivedDocumentsByEmployee(id);
    }

    @PostMapping("/newDocument")
    @ResponseBody
    public DocumentResponseDto newDocument(@RequestBody DocumentRequestDto documentRequestDto) {
        return documentService.addDocument(documentRequestDto);
    }

    @PutMapping("addToEmployee/{documentId}/{employeeId}")
    public void addToEmployee(@PathVariable Long documentId, @PathVariable Long employeeId) {
        documentService.documentToEmployee(documentId, employeeId);
    }

    @PutMapping("addTPost/{documentId}/{postId}")
    public void addToPost(@PathVariable Long documentId, @PathVariable Long postId) {
        documentService.documentToPost(documentId, postId);
    }

    @PutMapping("/update/{id}")
    public void updateDocument(@RequestBody DocumentRequestDto documentRequestDto, @PathVariable Long id) {
        documentService.updateDocument(documentRequestDto, id);
    }

    @DeleteMapping("/archive/{id}")
    public void archiveDocument(@PathVariable Long id) {
        documentService.archivedocument(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveDocument(@PathVariable Long id) {
        documentService.noarchivedocument(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }


    @PutMapping("/uploadDocument/{id}")

    public DocumentResponseDto uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
        Document document = null;

        document = documentService.saveDocument(id, file);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/RH/documents/download/").path(document.getId().toString()).toUriString();
        System.out.println(downloadUrl);
        document.setDownloadUrl(downloadUrl);
        return documentMapper.documentToDocumentResponseDto(document);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        DocumentResponseDto document = null;
        document = documentService.getDocumentById(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "document; name=\"" + document.getName() + "\"").body(new ByteArrayResource(document.getData()));
    }

}
