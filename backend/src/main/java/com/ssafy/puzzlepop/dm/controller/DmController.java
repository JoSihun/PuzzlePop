package com.ssafy.puzzlepop.dm.controller;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.service.DmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: ControllerAdvice 적용하기
// TODO: exception 발생 시 body에 에러 메세지 그대로 보내는 부분 리팩토링

@RestController
@RequestMapping("/dm")
public class DmController {

    private final DmService dmService;

    @Autowired
    private DmController(DmService dmService) {
        this.dmService = dmService;
    }

    ////////

    @PostMapping
    public ResponseEntity<?> sendDm(@RequestBody DmCreateDto dmCreateDto) {
        try {
            int id = dmService.createDm(dmCreateDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDm(@RequestBody DmUpdateDto dmUpdateDto) {
        try {
            int id = dmService.updateDm(dmUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDm(@PathVariable int id) {
        try {
            dmService.deleteDm(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDmById(@PathVariable int id) {
        try {
            DmDto dmDto = dmService.getDmById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////

    @PostMapping("/list")
    public ResponseEntity<?> findDmsByFriendId(@RequestBody DmReadRequestDto dmReadRequestDto) {
        try {
            List<DmReadResponseDto> dmList = dmService.getDmsByFriendId(dmReadRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(dmList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
