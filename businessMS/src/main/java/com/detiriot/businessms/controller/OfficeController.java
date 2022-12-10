package com.detiriot.businessms.controller;

import com.detiriot.businessms.dto.OfficeRequestDto;
import com.detiriot.businessms.dto.OfficeResponseDto;
import com.detiriot.businessms.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/business/offices")
public class OfficeController {
    final OfficeService officeService;

    @GetMapping
    public List<OfficeResponseDto> getall() {
        return officeService.getAll();
    }

    @GetMapping("/byCompany/{id}")
    public List<OfficeResponseDto> getByCompany(@PathVariable Long id) {
        return officeService.getAllByCompany(id);
    }

    @GetMapping("/active/byCompany/{id}")
    public List<OfficeResponseDto> getActiveByCompany(@PathVariable Long id) {
        return officeService.getActiveByCompany(id);
    }

    @GetMapping("/archived/byCompany/{id}")
    public List<OfficeResponseDto> getArchivedByCompany(@PathVariable Long id) {
        return officeService.getArchivedByCompany(id);
    }

    @GetMapping("/byid/{id}")
    public OfficeResponseDto getById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }

    @PostMapping("/newOffice")
    public OfficeResponseDto newOffice(@RequestBody OfficeRequestDto officeRequestDto) {
        return officeService.addOffice(officeRequestDto);
    }

    @PutMapping("/update/{id}")
    public OfficeResponseDto update(@RequestBody OfficeRequestDto officeRequestDto, @PathVariable Long id) {
        return officeService.UpdateOffice(officeRequestDto, id);
    }

    @PutMapping("/addToCompany/{idOffice}/{idCompany}")
    public void addToCompany(@PathVariable Long idOffice, @PathVariable Long idCompany) {
        officeService.affectationOfficeToCompany(idOffice, idCompany);
    }

    @PutMapping("/archive/{id}")
    public void archive(@PathVariable Long id) {
        officeService.archiveOffice(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        officeService.deleteOffice(id);
    }

}
