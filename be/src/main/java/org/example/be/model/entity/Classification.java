package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Classification {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_metadata_id")
    private ImageMetadata imageMetadata;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classification_result_id")
    private ClassificationResult classificationResult;

    public Classification(String name,
                          LocalDateTime date,
                          ImageMetadata imageMetadata,
                          ClassificationResult classificationResult) {
        this.name = name;
        this.date = date;
        this.imageMetadata = imageMetadata;
        this.classificationResult = classificationResult;
    }

}
