package com.ditriot.mapper;

import com.ditriot.dto.DocumentRequestDto;
import com.ditriot.dto.DocumentResponseDto;
import com.ditriot.model.Document;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DocumentMapper {
   DocumentResponseDto documentToDocumentResponseDto(Document document);
   Document documentRequestDtoToDocument(DocumentRequestDto documentRequestDto);
}
