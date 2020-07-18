package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "classification_result")
public class ClassificationResult {

    private static final String ID_FIELD = "id";
    private static final String STATUS_FIELD = "status";
    private static final String TAGS_FIELD = "tags";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_FIELD)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = STATUS_FIELD)
    private StatusEnum status;

    @OneToMany
    @JoinColumn(name = TAGS_FIELD)
    private List<ClassificationTag> tags;

    public ClassificationResult(StatusEnum status) {
        this.status = status;
    }

}
