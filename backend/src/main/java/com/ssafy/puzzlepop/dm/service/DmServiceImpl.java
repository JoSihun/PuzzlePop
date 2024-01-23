package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.repository.DmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmServiceImpl implements DmService {

    private final DmRepository dmRepository;

    @Autowired
    private DmServiceImpl(DmRepository dmRepository) {
        this.dmRepository = dmRepository;
    }

    /////


}
