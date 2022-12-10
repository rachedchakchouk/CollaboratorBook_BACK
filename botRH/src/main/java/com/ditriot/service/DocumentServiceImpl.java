package com.ditriot.service;

import com.ditriot.dto.DocumentRequestDto;
import com.ditriot.dto.DocumentResponseDto;
import com.ditriot.mapper.DocumentMapper;
import com.ditriot.model.DocType;
import com.ditriot.model.Document;
import com.ditriot.model.Employee;
import com.ditriot.model.Post;
import com.ditriot.repo.DocumentRepo;
import com.ditriot.repo.EmployeeRepo;
import com.ditriot.repo.PostRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    final DocumentRepo documentRepo;
    final EmployeeRepo employeeRepo;
    final DocumentMapper documentMapper;
    final PostRepo postRepo;

    public DocumentServiceImpl(EmployeeRepo employeeRepo, DocumentRepo documentRepo, DocumentMapper documentMapper, PostRepo postRepo) {
        this.documentMapper = documentMapper;
        this.documentRepo = documentRepo;
        this.employeeRepo = employeeRepo;
        this.postRepo = postRepo;
    }

    @Override
    public DocumentResponseDto addDocument(DocumentRequestDto documentRequestDto) {
        Document document = documentMapper.documentRequestDtoToDocument(documentRequestDto);
        document.setArchived(false);
        Document addDocument = documentRepo.save(document);
        DocumentResponseDto documentResponseDto = documentMapper.documentToDocumentResponseDto(addDocument);
        return documentResponseDto;
    }

    @Override
    public List<DocumentResponseDto> getAll() {
        List<Document> documents = documentRepo.findAll();
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());
        return documentResponseDtos;
    }

    @Override
    public DocumentResponseDto getDocumentById(Long id) {
        Document document = documentRepo.findById(id).get();

        return documentMapper.documentToDocumentResponseDto(document);
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByEmployee(Long idE) {
        Employee employee = employeeRepo.findById(idE).get();
        List<Document> documents = documentRepo.findByEmployee(employee);
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());

        return documentResponseDtos;
    }

    @Override
    public List<DocumentResponseDto> getActiveDocumentsByEmployee(Long idE) {
        Employee employee = employeeRepo.findById(idE).get();
        List<Document> documents = documentRepo.findDocumentsByEmployeeAndArchived(employee, false);
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());

        return documentResponseDtos;
    }

    @Override
    public List<DocumentResponseDto> getArchivedDocumentsByEmployee(Long idE) {
        Employee employee = employeeRepo.findById(idE).get();
        List<Document> documents = documentRepo.findDocumentsByEmployeeAndArchived(employee, true);
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());

        return documentResponseDtos;
    }

    @Override
    public List<DocumentResponseDto> getActivePorfilImgByEmployee(Long id) {
        Employee employee = employeeRepo.findById(id).get();
        List<Document> documents = documentRepo.findDocumentsByEmployeeAndArchivedAndDocType(employee, false,DocType.PROFIL_IMG);
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());

        return documentResponseDtos;
    }

    @Override
    public List<DocumentResponseDto> getArchivedPorfilImgByEmployee(Long id) {
        Employee employee = employeeRepo.findById(id).get();
        List<Document> documents = documentRepo.findDocumentsByEmployeeAndArchivedAndDocType(employee, true,DocType.PROFIL_IMG);
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());
        return documentResponseDtos;
    }


    @Override
    public List<DocumentResponseDto> archiveDocumentsWithEmployee(Long idE) {
        Employee employee = employeeRepo.findById(idE).get();
        List<Document> documents = null;

        if (employee.getArchived() == true) {
            documents = documentRepo.findByEmployee(employee);
            for (Document document : documents
            ) {
                document.setArchived(true);
                documentRepo.save(document);

            }
        }
        List<DocumentResponseDto> documentResponseDtos = documents.stream().map(document -> documentMapper.documentToDocumentResponseDto(document)).collect(Collectors.toList());
        return documentResponseDtos;
    }

    @Override
    public void documentToEmployee(Long documentId, Long employeeId) {
        Document document = documentRepo.findById(documentId).get();
        Employee employee = employeeRepo.findById(employeeId).get();
        document.setEmployee(employee);
        documentRepo.save(document);
    }

    @Override
    public void documentToPost(Long documentId, Long postId) {
        Document document = documentRepo.findById(documentId).get();
        Post post = postRepo.findById(postId).get();
        List<Post> posts = document.getPosts();
        posts.add(post);
        document.setPosts(posts);
        document.setDocType(DocType.POST_IMG);
        documentRepo.save(document);
    }

    @Override
    public void archivedocument(Long id) {
        Document document = documentRepo.findById(id).get();
        document.setArchived(true);
        documentRepo.save(document);


    }

    @Override
    public void noarchivedocument(Long id) {
        Document document = documentRepo.findById(id).get();
        document.setArchived(false);
        documentRepo.save(document);

    }

    @Override
    public void updateDocument(DocumentRequestDto documentRequestDto, Long id) {
        Document document = documentRepo.findById(id).get();
        documentRequestDto.setId(document.getId());
        Document updatedDocument = documentMapper.documentRequestDtoToDocument(documentRequestDto);
        documentRepo.save(updatedDocument);

    }

    @Override
    public void deleteDocument(Long id) {
        Document document = documentRepo.findById(id).get();
        documentRepo.delete(document);

    }

    @Override
    public Document saveDocument(Long id, MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence" + fileName);
            }
            Document document = documentRepo.findById(id).get();
            document.setName(fileName);
            document.setFileType(file.getContentType());
            document.setData(file.getBytes());
            return documentRepo.save(document);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }
}
