package com.ssafy.puzzlepop.dm.controller;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.service.DmService;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.service.FriendService;
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

    @EventListener
    public void handleDmSocketConnectListener(SessionConnectEvent event) {
        System.out.println("DM : new user connected to socket");
        System.out.println("sessionid : " + event.getMessage().getHeaders().get("simpSessionId"));
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("DM : user disconnected to socket");
        System.out.println("sessionid : " + event.getSessionId());
    }

    @MessageMapping("/dm/send/{friendId}") // /app/send/{friendId}에 대해 여기로 들어옴
    public void sendDm(@DestinationVariable Long friendId, DmCreateDto dmCreateDto) {

        try {
            DmReadResponseDto dmResponseDto = dmService.createDm(dmCreateDto);
            sendingOperations.convertAndSend("/queue/receive/"+friendId.toString(), dmResponseDto);
        } catch (Exception e) {
            // TODO: exception handling 필요
        }
    }

    //////////

    /* http */

    @PostMapping("/list")
    public ResponseEntity<?> findDmsByUserIdAndFriendUserId(@RequestBody DmReadRequestDto dmReadRequestDto) {
        try {
            List<DmReadResponseDto> dmList = dmService.getDmsByUserIdAndFriendUserId(dmReadRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(dmList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //    @PutMapping
    public ResponseEntity<?> updateDm(@RequestBody DmUpdateDto dmUpdateDto) {
        try {
            Long id = dmService.updateDm(dmUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDm(@PathVariable Long id) {
        try {
            dmService.deleteDm(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //    @GetMapping("/{id}")
    public ResponseEntity<?> findDmById(@PathVariable Long id) {
        try {
            DmDto dmDto = dmService.getDmById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //////////

}
