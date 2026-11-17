package com.housemanager.service;

import com.housemanager.data.entity.Payment;
import com.housemanager.dto.PaymentDTO;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public interface PaymentService {
    void Pay(long id);

    List<PaymentDTO> generateMonthlyPayments(long buildingId, YearMonth month);

    BigDecimal getOwnedAmountByBuilding(long buildingId);

    BigDecimal getOwnedAmountByCompany(long companyId);

    BigDecimal getOwnedAmountByEmployee(long employeeId);

    BigDecimal getPaidAmountByBuilding(long buildingId);

    BigDecimal getPaidAmountByCompany(long companyId);

    BigDecimal getPaidAmountByEmployee(long employeeId);

}
