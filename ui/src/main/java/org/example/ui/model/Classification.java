package org.example.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Classification implements Serializable {

    private static final long serialVersionUID = -1518659209851785840L;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "url")
    private String url;

}
