package com.ssafy.puzzlepop.dm.aop;

import com.ssafy.puzzlepop.dm.controller.DmController;
import com.ssafy.puzzlepop.dm.exception.DmBadRequestException;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.exception.DmNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
public class DmExceptionHandler {

    @MessageExceptionHandler(Exception.class)
    public Message<byte[]> handleMessageException(Exception e) {
        System.out.println("message exception occurred");

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(e.getMessage().getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

    @ExceptionHandler(DmBadRequestException.class)
    public ResponseEntity<?> handleDmBadRequestException(Exception e) {
        System.out.println("dm bad request exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(DmNotFoundException.class)
    public ResponseEntity<?> handleDmNotFoundException(Exception e){
        System.out.println("dm not found exception");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(DmException.class)
    public ResponseEntity<?> handleDmException(Exception e) {
        System.out.println("dm exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // TODO: 추후 백엔드 통합할 때 불필요해지면 삭제
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        System.out.println("general exception handling");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
