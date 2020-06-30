package org.example.ui.controller;

import org.example.ui.model.ClassificationRequest;
import org.example.ui.model.FormDataFile;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

@ApplicationScoped
public class MultipartFormDataService {

    public ClassificationRequest getClassificationRequest(MultipartFormDataInput input) {
        try {
            final FormDataFile formDataFile = getFormDataFile(input);

            return new ClassificationRequest(
                    getClassificationName(input),
                    getClassificationDescription(input),
                    formDataFile.getFile(),
                    formDataFile.getName(),
                    formDataFile.getMimeType()
            );
        } catch (IOException ex) {
            throw new NullPointerException(ex.getMessage());
        }
    }

    private String getClassificationName(MultipartFormDataInput input) throws IOException {
        return input.getFormDataPart("name", String.class, null);
    }

    private String getClassificationDescription(MultipartFormDataInput input) throws IOException {
        return input.getFormDataPart("description", String.class, null);
    }

    private FormDataFile getFormDataFile(MultipartFormDataInput input) throws IOException {
        final InputPart inputPart = input.getFormDataMap().get("file").get(0);

        return new FormDataFile(getFormDataFileInputStream(inputPart), getFormDataFileName(inputPart), getFormDataFileMimeType(inputPart));
    }

    private InputStream getFormDataFileInputStream(InputPart inputPart) throws IOException {
        return inputPart.getBody(InputStream.class, null);
    }

    private String getFormDataFileName(InputPart inputPart) {
        final MultivaluedMap<String, String> headers = inputPart.getHeaders();
        final String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        return Stream.of(contentDispositionHeader)
                .filter(name -> name.trim().startsWith("filename"))
                .map(name -> {
                    final String[] tmp = name.split("=");
                    return tmp[1].trim().replaceAll("\"", "");
                })
                .findFirst()
                .orElseThrow(() -> new NullPointerException("filename cannot be null"));
    }

    private String getFormDataFileMimeType(InputPart inputPart) {
        return inputPart.getMediaType().toString();
    }

}
