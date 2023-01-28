package com.ditriot.controller;

import com.ditriot.dto.ContractRequestDto;
import com.ditriot.dto.ContractResponseDto;
import com.ditriot.dto.EmployeeResponseDto;
import com.ditriot.mapper.ContractMapper;
import com.ditriot.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/contracts")
public class ContractController {
    final ContractService contractService;
    final ContractMapper contractMapper;

    @GetMapping
    public List<ContractResponseDto> getcontracts()
    {
        return contractService.getAll();
    }
    @GetMapping("/{id}")
    public ContractResponseDto getContractById(@PathVariable Long id){
        return contractService.getContractById(id);
    }
    @GetMapping("/byEmployer/{id}")
    public List<ContractResponseDto> getcontractsByEmployer(@PathVariable Long id){return contractService.getContractsByEmployer(id);}
    @GetMapping("/active/byEmployer/{id}")
    public List<ContractResponseDto> getActivecontractsByEmployer(@PathVariable Long id){return contractService.getActiveContractsByEmployer(id);}
    @GetMapping("/archived/byEmployer/{id}")
    public List<ContractResponseDto> getArchivedcontractsByEmployer(@PathVariable Long id){return contractService.getArchivedContractsByEmployer(id);}
    @GetMapping("/byEmplyee/{id}")
    public List<ContractResponseDto> getcontractsByEmployee(@PathVariable Long id){return contractService.getContractsByEmployee(id);}
    @GetMapping("/active/byEmplyee/{id}")
    public List<ContractResponseDto> getActivecontractsByEmployee(@PathVariable Long id){return contractService.getActiveContractsByEmployee(id);}
    @GetMapping("/archived/byEmplyee/{id}")
    public List<ContractResponseDto> getArchivedcontractsByEmployee(@PathVariable Long id){return contractService.getArchivedContractsByEmployee(id);}
    @PostMapping("/newcontract")
    public ContractResponseDto newcontract(@RequestBody ContractRequestDto contractRequestDto){return contractService.addContract(contractRequestDto);}
    @PutMapping ("/addtoEmployer/{idContract}/{idEmployer}")
    public void addToEmployer(@PathVariable Long idContract,@PathVariable Long idEmployer ){contractService.contractToEmployer(idContract,idEmployer);}
    @PutMapping ("/addtoEmployee/{idEmployee}/{idContract}")
    public void addToEmployee(@PathVariable Long idEmployee,@PathVariable Long idContract ){contractService.employeeToContract(idEmployee,idContract);}
    @PutMapping("/update/{id}")
    public  ContractResponseDto updateContract(@RequestBody ContractRequestDto contractRequestDto,@PathVariable Long id){return contractService.updateContract(contractRequestDto,id);}
    @DeleteMapping("/archive/{id}")
    public void archiveContract(@PathVariable Long id){contractService.archiveContract(id);}
    @DeleteMapping("/noarchive/{id}")
    public void noarchiveContract(@PathVariable Long id){contractService.noarchiveContract(id);}
    @DeleteMapping("/delete/{id}")
    public void deleteContract(@PathVariable Long id){contractService.deleteContract(id);}

@GetMapping("/employeeFormContract/{id}")
    public EmployeeResponseDto getEmployeeFormContract(@PathVariable Long id){
        return contractService.getEmployeeFromContract(id);
}





}
