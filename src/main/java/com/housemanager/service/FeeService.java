package com.housemanager.service;

import com.housemanager.dto.FeeDto;

import java.util.List;

public interface FeeService {
    List<FeeDto> getAllFees();

    FeeDto getFeeById(long id);

    FeeDto createFee(FeeDto fee);

    FeeDto updateFee(FeeDto fee, long id);

    void deleteFee(long id);
}
