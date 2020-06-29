package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "classification_result")
public class ClassificationResult {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public ClassificationResult(StatusEnum status) {
        this.status = status;
    }

}
