package com.ditriot.controller;

import com.ditriot.dto.HoldayRequestDto;
import com.ditriot.dto.HoldayResponseDto;
import com.ditriot.mapper.HoldayMapper;
import com.ditriot.service.HoldayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequiredArgsConstructor
@RequestMapping("/RH/holidays")
public class HoldayController {
    final HoldayService holdayService;
    final HoldayMapper holdayMapper;

    @GetMapping
    @ResponseBody
    public List<HoldayResponseDto> getHoldays() {
        return holdayService.getAll();
    }

    @GetMapping("/byId/{id}")
    @ResponseBody
    public HoldayResponseDto getbyId(@PathVariable Long id) {
        return holdayService.getHoldayById(id);
    }

    @GetMapping("/byemployee/{id}")
    @ResponseBody
    public List<HoldayResponseDto> getByEmployee(@PathVariable Long id) {
        return holdayService.getbyEmployee(id);
    }

    @GetMapping("/active/byemployee/{id}")
    @ResponseBody
    public List<HoldayResponseDto> getActiveByEmployee(@PathVariable Long id) {
        return holdayService.getActiveByEmployee(id);
    }

    @GetMapping("/archived/byemployee/{id}")
    @ResponseBody
    public List<HoldayResponseDto> getArchivedByEmployee(@PathVariable Long id) {
        return holdayService.getArchiveByEmployee(id);
    }

 @PostMapping("/newHoliday/{idEmployee}")
 public HoldayResponseDto newHolday(@RequestBody HoldayRequestDto holdayRequestDto,@PathVariable Long idEmployee) {
        return holdayService.addHolday(holdayRequestDto,idEmployee);
    }

    @PutMapping("/holidaytoemployee/{idHolday}/{idEmployee}")
    public void addToEmployee(@PathVariable Long idHolday, @PathVariable Long idEmployee) {
        holdayService.holdayToEmployee(idHolday, idEmployee);
    }

    @PutMapping("/upadte/{id}")
    @ResponseBody
    public HoldayResponseDto update(@RequestBody HoldayRequestDto holdayRequestDto, @PathVariable Long id) {
        return holdayService.updateHolday(holdayRequestDto, id);
    }

    @DeleteMapping("/archive/{id}")
    public void archive(@PathVariable Long id) {
        holdayService.archivedHolday(id);
    }
    @DeleteMapping("/noarchive/{id}")
    public void noarchive(@PathVariable Long id) {
        holdayService.noarchivedHolday(id);
    }

    @DeleteMapping("/prouve/{id}")
    public void prouve(@PathVariable Long id) {
        holdayService.approveHolday(id);
    }

    @DeleteMapping("/refuse/{id}")
    public void refuse(@PathVariable Long id) {
        holdayService.refuseHolday(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        holdayService.deleteHolday(id);
    }


}
