package com.housemanager.data.repo;

import com.housemanager.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByApartmentIdAndPeriod(Long apartmentId, LocalDate period);
    Optional<Payment> findFirstByApartmentIdAndPaidTrueOrderByPaymentDateDesc(Long apartmentId);
    List<Payment> findAllByApartmentBuildingIdAndPaidFalse(Long buildingId);
    List<Payment> findAllByApartmentBuildingIdAndPaidTrue(Long buildingId);
}
