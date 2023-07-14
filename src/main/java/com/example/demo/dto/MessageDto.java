package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime dateTime;
}
