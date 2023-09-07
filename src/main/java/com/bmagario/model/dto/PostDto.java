package com.bmagario.model.dto;

import java.time.LocalDateTime;

public record PostDto(String title, String content, LocalDateTime creationDate) {
}
