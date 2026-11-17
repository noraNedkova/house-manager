package com.housemanager.web.api;

import com.housemanager.dto.FeeDto;
import com.housemanager.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fees")
public class FeeApiController {
    private final FeeService feeService;

    @GetMapping
    public List<FeeDto> getFees() {
        return feeService.getAllFees();
    }

    @GetMapping("/{id}")
    public FeeDto getFee(@PathVariable Long id) {
        return feeService.getFeeById(id);
    }

    @PostMapping
    public FeeDto createFee(@RequestBody FeeDto fee) {
        return feeService.createFee(fee);
    }

    @PutMapping("/{id}")
    public FeeDto updateFee(@RequestBody FeeDto fee, @PathVariable Long id) {
        return feeService.updateFee(fee, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFee(@PathVariable long id) {
        feeService.deleteFee(id);
    }
}
