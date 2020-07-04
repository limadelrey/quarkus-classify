package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class Classification {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String CREATED_AT_FIELD = "created_at";
    public static final String IMAGE_METADATA_ID_FIELD = "image_metadata_id";
    public static final String CLASSIFICATION_RESULT_ID_FIELD = "classification_result_id";

    @Id
    @Column(name = ID_FIELD)
    private UUID id;

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = DESCRIPTION_FIELD)
    private String description;

    @Column(name = CREATED_AT_FIELD)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IMAGE_METADATA_ID_FIELD)
    private ImageMetadata imageMetadata;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CLASSIFICATION_RESULT_ID_FIELD)
    private ClassificationResult classificationResult;

    public Classification(UUID id,
                          String name,
                          String description,
                          LocalDateTime createdAt,
                          ImageMetadata imageMetadata,
                          ClassificationResult classificationResult) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.imageMetadata = imageMetadata;
        this.classificationResult = classificationResult;
    }

}
