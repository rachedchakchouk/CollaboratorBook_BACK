package com.ditriot.repo;

import com.ditriot.model.DocType;
import com.ditriot.model.Document;
import com.ditriot.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepo extends JpaRepository<Document,Long> {
List<Document> findByEmployee(Employee employee);
List<Document> findDocumentsByEmployeeAndArchived(Employee employee,Boolean b);
List<Document> findDocumentsByEmployeeAndArchivedAndDocType(Employee employee, Boolean b, DocType docType);
    }
