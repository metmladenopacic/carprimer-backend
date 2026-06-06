package com.metropolitan.service.impl;

import com.metropolitan.entity.Owner;
import com.metropolitan.repository.OwnerRepository;
import com.metropolitan.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }


}

