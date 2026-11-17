package com.housemanager.service.impl;

import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Fee;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.FeeRepository;
import com.housemanager.dto.FeeDto;
import com.housemanager.service.FeeService;
import com.housemanager.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final BuildingRepository buildingRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<FeeDto> getAllFees() {
        return this.mapperUtil.mapList(feeRepository.findAll(), FeeDto.class);
    }

    @Override
    public FeeDto getFeeById(long id) {
        return this.mapperUtil.map(feeRepository.findById(id), FeeDto.class);
    }

    @Override
    public FeeDto createFee(FeeDto feeDto) {

        Building building = buildingRepository.findById(feeDto.getBuildingId())
                .orElseThrow(() ->
                        new RuntimeException("Building not found with id: " + feeDto.getBuildingId()));

        Fee fee = mapperUtil.getModelMapper().map(feeDto, Fee.class);
        fee.setBuilding(building);

        return mapperUtil.map(feeRepository.save(fee), FeeDto.class);
    }

    @Override
    public FeeDto updateFee(FeeDto feeDto, long id) {

        Fee fee = feeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Fee not found with id: " + id));

        fee.setPricePerSquareMeter(feeDto.getPricePerSquareMeter());
        fee.setElevatorFee(feeDto.getElevatorFee());
        fee.setPetFee(feeDto.getPetFee());

        Fee updatedFee = feeRepository.save(fee);

        return mapperUtil.map(updatedFee, FeeDto.class);
    }

    @Override
    public void deleteFee(long id) {
        this.feeRepository.deleteById(id);
    }
}