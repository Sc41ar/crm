package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MesssageDTO {

    /**
     * Поле для хранения адресата сообщения.
     */
    private String destination;

    /**
     * Поле для хранения темы сообщения.
     */
    private String subject;

    /**
     * Поле для хранения текста сообщения.
     */
    private String body;

}