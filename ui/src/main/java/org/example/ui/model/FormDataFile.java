package org.example.ui.model;

import lombok.Data;

import java.io.InputStream;

@Data
public class FormDataFile {

    private final InputStream file;
    private final String name;
    private final String mimeType;

}
