package com.housemanager.service;

import com.housemanager.dto.OwnerDTO;

import java.util.List;

public interface OwnerService {

    List<OwnerDTO> getAllOwners();

    OwnerDTO getOwnerById(long id);

    OwnerDTO createOwner(OwnerDTO owner);

    OwnerDTO updateOwner(OwnerDTO owner, long id);

    void deleteOwner(long id);
}
