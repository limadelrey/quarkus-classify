package org.example.be.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "image_metadata")
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long size;
    private String format;
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
