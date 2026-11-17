package com.housemanager.web.api;

import com.housemanager.dto.OwnerDTO;
import com.housemanager.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerApiController {
    private final OwnerService ownerService;

    @GetMapping
    public List<OwnerDTO> getOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public OwnerDTO getOwner(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @PostMapping
    public OwnerDTO createOwner(@RequestBody OwnerDTO owner) {
        return ownerService.createOwner(owner);
    }

    @PutMapping("/{id}")
    public OwnerDTO updateOwner(@RequestBody OwnerDTO owner,@PathVariable Long id) {
        return ownerService.updateOwner(owner, id);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable long id) {
        ownerService.deleteOwner(id);
    }
}
