package org.example.ui.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageClassification {

    private final String url;
    private final String name;
    private final LocalDateTime createdAt;
    private final Status status;

}
