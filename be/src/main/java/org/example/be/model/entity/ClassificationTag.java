package org.example.be.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "classification_tag")
public class ClassificationTag {

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String CONFIDENCE_FIELD = "confidence";
    private static final String CLASSIFICATION_RESULT_ID_FIELD = "classification_result_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_FIELD)
    private Long id;

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = CONFIDENCE_FIELD)
    private Double confidence;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CLASSIFICATION_RESULT_ID_FIELD)
    private ClassificationResult classificationResult;

    public ClassificationTag(String name,
                             Double confidence,
                             ClassificationResult classificationResult) {
        this.name = name;
        this.confidence = confidence;
        this.classificationResult = classificationResult;
    }

}
