package org.example.ui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ImageClassification implements Serializable {

    private static final long serialVersionUID = -1518659209851785840L;

    @JsonProperty(value = "url")
    private String url;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "status")
    private String status;

}
