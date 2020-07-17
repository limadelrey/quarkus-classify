package org.example.ai.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.ai.classification.ImageClassificationPython;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassificationService {

    @ConfigProperty(name = "api.key")
    String apiKey;

    @ConfigProperty(name = "api.secret")
    String apiSecret;

    @ConfigProperty(name = "authorization")
    String authorization;

    public String classificateImage(final String imageUrl) {
        return ImageClassificationPython.TAGS.execute(apiKey, apiSecret, authorization, imageUrl);
    }
}
