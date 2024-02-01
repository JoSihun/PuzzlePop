package com.ssafy.puzzlepop.dm.controller;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.service.DmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: exception 발생 시 body에 에러 메세지 그대로 보내는 부분 리팩토링

@RestController
public class DmController {

    private final DmService dmService;

    @Autowired
    private DmController(DmService dmService) {
        this.dmService = dmService;
    }

    ////////

//    @PostMapping
    @MessageMapping("/")
    @SendTo("/room/{id}")
    public DmReadResponseDto sendDm(DmCreateDto dmCreateDto) {
        try {
            Long id = dmService.createDm(dmCreateDto);

            DmReadResponseDto dmDto = new DmReadResponseDto();
            dmDto.setId(id);
            dmDto.setFromUserId(dmCreateDto.getFromUserId());
            dmDto.setToUserId(dmCreateDto.getToUserId());
            dmDto.setContent(dmCreateDto.getContent());

            return dmDto;
        } catch (Exception e) {
            return null;
        }
    }

//    @PutMapping
    public ResponseEntity<?> updateDm(@RequestBody DmUpdateDto dmUpdateDto) {
        try {
            Long id = dmService.updateDm(dmUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDm(@PathVariable Long id) {
        try {
            dmService.deleteDm(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @GetMapping("/{id}")
    public ResponseEntity<?> findDmById(@PathVariable Long id) {
        try {
            DmDto dmDto = dmService.getDmById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////

    @PostMapping("/enter")
    public ResponseEntity<?> findDmsByFriendId(@RequestBody DmReadRequestDto dmReadRequestDto) {
        try {
            List<DmReadResponseDto> dmList = dmService.getDmsByFriendId(dmReadRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(dmList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
