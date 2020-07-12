package org.example.be.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

import static org.example.be.model.json.ClassificationRequest.*;

@Data
@JsonPropertyOrder({CLASSIFICATION_NAME_FIELD, CLASSIFICATION_DESCRIPTION_FIELD, FILE_FIELD, FILE_FIELD, FILE_NAME_FIELD, MIME_TYPE_FIELD})
public class ClassificationRequest {

    private static final String NOT_NULL_MESSAGE = " cannot be null";
    private static final String NOT_BLANK_MESSAGE = " cannot be null or empty";

    public static final String CLASSIFICATION_NAME_FIELD = "classification_name";
    public static final String CLASSIFICATION_DESCRIPTION_FIELD = "classification_description";
    public static final String FILE_FIELD = "file";
    public static final String FILE_NAME_FIELD = "file_name";
    public static final String MIME_TYPE_FIELD = "mime_type";

    @FormParam(CLASSIFICATION_NAME_FIELD)
    @PartType(MediaType.TEXT_PLAIN)
    @JsonProperty(CLASSIFICATION_NAME_FIELD)
    @NotBlank(message = CLASSIFICATION_NAME_FIELD + NOT_BLANK_MESSAGE)
    private String classificationName;

    @FormParam(CLASSIFICATION_DESCRIPTION_FIELD)
    @PartType(MediaType.TEXT_PLAIN)
    @JsonProperty(CLASSIFICATION_DESCRIPTION_FIELD)
    @NotNull(message = CLASSIFICATION_DESCRIPTION_FIELD + NOT_NULL_MESSAGE)
    private String classificationDescription;

    @FormParam(FILE_FIELD)
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @JsonProperty(FILE_FIELD)
    @NotNull(message = FILE_FIELD + NOT_NULL_MESSAGE)
    private InputStream file;

    @FormParam(FILE_NAME_FIELD)
    @PartType(MediaType.TEXT_PLAIN)
    @JsonProperty(FILE_NAME_FIELD)
    @NotBlank(message = FILE_NAME_FIELD + NOT_BLANK_MESSAGE)
    private String fileName;

    @FormParam(MIME_TYPE_FIELD)
    @PartType(MediaType.TEXT_PLAIN)
    @JsonProperty(MIME_TYPE_FIELD)
    @NotBlank(message = MIME_TYPE_FIELD + NOT_BLANK_MESSAGE)
    private String mimeType;

}
