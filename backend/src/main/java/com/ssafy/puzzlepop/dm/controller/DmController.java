package com.ssafy.puzzlepop.dm.controller;

import com.ssafy.puzzlepop.dm.service.DmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DmController {

    private final DmService dmService;

    @Autowired
    private DmController(DmService dmService) {
        this.dmService = dmService;
    }


}
