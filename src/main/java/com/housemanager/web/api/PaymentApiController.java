package com.housemanager.web.api;

import com.housemanager.dto.PaymentDTO;
import com.housemanager.service.PaymentService;
import com.housemanager.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentApiController {
    private final PaymentService paymentService;

    @PostMapping("/generate")
    public List<PaymentDTO> generatePayments(@RequestParam long buildingId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return paymentService.generateMonthlyPayments(buildingId, month);
    }

    @PostMapping("/{id}/pay")
    public void pay(@PathVariable long id) {
        paymentService.Pay(id);
    }

    @GetMapping("/owned_by_building/{id}")
    public BigDecimal getOwnedAmountByBuilding(@PathVariable long id) {
        return paymentService.getOwnedAmountByBuilding(id);
    }

    @GetMapping("/owned_by_company/{id}")
    public BigDecimal getOwnedAmountByCompany(@PathVariable long id) {
        return paymentService.getOwnedAmountByCompany(id);
    }

    @GetMapping("/owned_by_employee/{id}")
    public BigDecimal getOwnedAmountByEmployee(@PathVariable long id) {
        return paymentService.getOwnedAmountByEmployee(id);
    }

    @GetMapping("/paid_by_building/{id}")
    public BigDecimal getPaisAmountByBuilding(@PathVariable long id) {
        return paymentService.getPaidAmountByBuilding(id);
    }

    @GetMapping("/paid_by_company/{id}")
    public BigDecimal getPaidAmountByCompany(@PathVariable long id) {
        return paymentService.getPaidAmountByCompany(id);
    }

    @GetMapping("/paid_by_employee/{id}")
    public BigDecimal getPaidAmountByEmployee(@PathVariable long id) {
        return paymentService.getPaidAmountByEmployee(id);
    }
}
