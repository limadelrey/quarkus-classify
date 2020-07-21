package org.example.ai.model.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ClassificationTag {

    private final String name;
    private final Double confidence;

}
