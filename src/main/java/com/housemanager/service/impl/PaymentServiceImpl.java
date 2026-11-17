package com.housemanager.service.impl;

import com.housemanager.data.entity.*;
import com.housemanager.data.repo.ApartmentRepository;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.FeeRepository;
import com.housemanager.data.repo.PaymentRepository;
import com.housemanager.dto.PaymentDTO;
import com.housemanager.service.PaymentFileService;
import com.housemanager.service.PaymentService;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApartmentRepository apartmentRepository;
    private final FeeRepository feeRepository;
    private final PaymentFileService paymentFileService;
    private final BuildingRepository  buildingRepository;
    private final MapperUtil mapperUtil;


    @Override
    public void Pay(long id) {
        Payment payment1 = this.paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        if(payment1.isPaid()) {
            throw new IllegalStateException("This fee is already paid");
        }

        payment1.setPaid(true);
        payment1.setPaymentDate(LocalDate.now());
        paymentRepository.save(payment1);

        paymentFileService.savePayment(payment1);

    }

    @Override
    public List<PaymentDTO> generateMonthlyPayments(long buildingId, YearMonth month) {
        Fee buildingFee = feeRepository.findByBuildingId(buildingId);

        LocalDate period = month.atDay(1);
        List<PaymentDTO> created = new ArrayList<>();

        for (Apartment a : apartmentRepository.findByBuildingId(buildingId)) {
            if (paymentRepository.existsByApartmentIdAndPeriod(a.getId(), period)) continue;

            Payment p = new Payment();
            p.setApartment(a);
            p.setPeriod(period);
            p.setAmount(calculate(a, buildingFee));
            p.setPaid(false);
            created.add(mapperUtil.getModelMapper()
                    .map(paymentRepository.save(p), PaymentDTO.class));

        }
        return created;
    }

    private BigDecimal calculate(Apartment a, Fee f) {
        long elevatorUsers = a.getResidents().stream()
                .filter(r -> Period.between(r.getBirthday(), LocalDate.now()).getYears() > 7)
                .filter(Resident::isUsesElevator)
                .count();

        long pets = a.getNumberOfPetsUsingCommonParts();

        return a.getApartmentArea().multiply(f.getPricePerSquareMeter())
                .add(BigDecimal.valueOf(elevatorUsers).multiply(f.getElevatorFee()))
                .add(BigDecimal.valueOf(pets).multiply(f.getPetFee()));
    }

    @Override
    public BigDecimal getOwnedAmountByBuilding(long buildingId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Payment payment : paymentRepository.findAllByApartmentBuildingIdAndPaidFalse(buildingId)) {
            total = total.add(payment.getAmount());
        }
        return total;
    }

    @Override
    public BigDecimal getOwnedAmountByCompany(long companyId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Building building : buildingRepository.findAllByCompanyId(companyId)) {
            total = total.add(getOwnedAmountByBuilding(building.getId()));
        }
        return total;
    }

    @Override
    public BigDecimal getOwnedAmountByEmployee(long employeeId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Building building : buildingRepository.findAllByEmployeeId(employeeId)) {
            total = total.add(getOwnedAmountByBuilding(building.getId()));
        }
        return total;
    }

    @Override
    public BigDecimal getPaidAmountByBuilding(long buildingId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Payment payment : paymentRepository.findAllByApartmentBuildingIdAndPaidTrue(buildingId)) {
            total = total.add(payment.getAmount());
        }
        return total;
    }

    @Override
    public BigDecimal getPaidAmountByCompany(long companyId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Building building : buildingRepository.findAllByCompanyId(companyId)) {
            total = total.add(getPaidAmountByBuilding(building.getId()));
        }
        return total;
    }

    @Override
    public BigDecimal getPaidAmountByEmployee(long employeeId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Building building : buildingRepository.findAllByEmployeeId(employeeId)) {
            total = total.add(getPaidAmountByBuilding(building.getId()));
        }
        return total;
    }
}
