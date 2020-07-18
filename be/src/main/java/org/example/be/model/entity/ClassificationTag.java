package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "classification_tag")
public class ClassificationTag {

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String CONFIDENCE_FIELD = "confidence";

    @Id
    @Column(name = ID_FIELD)
    private UUID id;

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = CONFIDENCE_FIELD)
    private Double confidence;

    public ClassificationTag(UUID id,
                             String name,
                             Double confidence) {
        this.id = id;
        this.name = name;
        this.confidence = confidence;
    }

}
