package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 4352938044937898340L;

    @JsonProperty(value = "error")
    private final String error;

//    public ErrorResponse(String error) {
//        this.error = error;
//    }

}
