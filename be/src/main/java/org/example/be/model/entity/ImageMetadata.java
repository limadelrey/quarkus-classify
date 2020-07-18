package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "image_metadata")
public class ImageMetadata {

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String SIZE_FIELD = "size";
    private static final String FORMAT_FIELD = "format";
    private static final String URL_FIELD = "url";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_FIELD)
    private Long id;

    @Column(name = NAME_FIELD)
    private String name;

    @Column(name = SIZE_FIELD)
    private Long size;

    @Column(name = FORMAT_FIELD)
    private String format;

    @Column(name = URL_FIELD)
    private String url;

    public ImageMetadata(String name,
                         Long size,
                         String format,
                         String url) {
        this.name = name;
        this.size = size;
        this.format = format;
        this.url = url;
    }

}
