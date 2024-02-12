package com.ssafy.puzzlepop.dm.controller;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmBadRequestException;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.exception.DmNotFoundException;
import com.ssafy.puzzlepop.dm.service.DmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

// TODO: exception 발생 시 body에 에러 메세지 그대로 보내는 부분 리팩토링

@RestController
@RequestMapping("/dm")
@CrossOrigin("*")
public class DmController {

    private final DmService dmService;
    private final SimpMessageSendingOperations sendingOperations;

    @Autowired
    private DmController(DmService dmService, SimpMessageSendingOperations sendingOperations) {
        this.dmService = dmService;
        this.sendingOperations = sendingOperations;
    }

    ////////

    /* Socket */

    @MessageMapping("/send/{friendId}") // /app/send/{friendId}에 대해 여기로 들어옴
    public void sendDm(@DestinationVariable Long friendId, DmCreateDto dmCreateDto) throws DmException, DmBadRequestException {
        DmReadResponseDto dmResponseDto = dmService.createDm(friendId, dmCreateDto);
        sendingOperations.convertAndSend("/queue/receive/" + friendId.toString(), dmResponseDto);
    }

    @EventListener
    public void handleDmSocketConnectListener(SessionConnectEvent event) {
        System.out.println("DM : new user connected to socket");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("DM : user disconnected to socket");
    }

    // 왜 작동 안 되는 건지 확인 필요
//    @SubscribeMapping("/queue/dm/receive/{friendId}")
//    public void subscribeDm(@DestinationVariable Long friendId) throws DmException {
//        System.out.println("subscribeDm : "+friendId);
//    }

    //////////

    /* http */

    @PostMapping // /app/send/{friendId}에 대해 여기로 들어옴
    public ResponseEntity<?> createDm(@RequestBody DmCreateDto dmCreateDto) throws DmException, DmBadRequestException {
        System.out.println(dmCreateDto);
        Long friendId = (long) 1;
        DmReadResponseDto dmResponseDto = dmService.createDm(friendId, dmCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(dmResponseDto);
    }


    @PostMapping("/list")
    public ResponseEntity<?> findDmsByUserIdAndFriendUserId(@RequestBody DmReadRequestDto dmReadRequestDto) throws DmException, DmBadRequestException {
        System.out.println(dmReadRequestDto);

        List<DmReadResponseDto> dmList = dmService.getDmsByUserIdAndFriendUserId(dmReadRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(dmList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDmById(@PathVariable Long id) throws DmException, DmNotFoundException {
        DmDto dmDto = dmService.getDmById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dmDto);
    }

//    @PutMapping
    public ResponseEntity<?> updateDm(@RequestBody DmUpdateDto dmUpdateDto) throws DmException, DmBadRequestException, DmNotFoundException {
        Long id = dmService.updateDm(dmUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

//    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDm(@PathVariable Long id) throws DmException, DmNotFoundException {
        dmService.deleteDm(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETE OK");
    }

    //////////

}
