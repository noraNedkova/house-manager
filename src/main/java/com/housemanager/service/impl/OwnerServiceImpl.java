package com.housemanager.service.impl;

import com.housemanager.data.entity.Owner;
import com.housemanager.data.repo.OwnerRepository;
import com.housemanager.dto.OwnerDTO;
import com.housemanager.service.OwnerService;
import com.housemanager.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<OwnerDTO> getAllOwners() {
        return this.mapperUtil.mapList(ownerRepository.findAll(), OwnerDTO.class);
    }

    @Override
    public OwnerDTO getOwnerById(long id) {
        return this.mapperUtil.map(ownerRepository.findById(id), OwnerDTO.class);
    }

    @Override
    public OwnerDTO createOwner(OwnerDTO owner) {

        return mapperUtil.getModelMapper()
                .map(this.ownerRepository
                        .save(mapperUtil.getModelMapper()
                                .map(owner, Owner.class)), OwnerDTO.class);
    }

    @Override
    public OwnerDTO updateOwner(OwnerDTO ownerDTO, long id) {

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Owner not found with id: " + id));

        mapperUtil.getModelMapper().map(ownerDTO, owner);

        Owner updatedOwner = ownerRepository.save(owner);

        return mapperUtil.map(updatedOwner, OwnerDTO.class);
    }

    @Override
    public void deleteOwner(long id) {
        this.ownerRepository.deleteById(id);
    }
}
