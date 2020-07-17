package org.example.ai.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.ai.classification.ImageClassificationPython;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClassificationService {

    @ConfigProperty(name = "api.key")
    String apiKey;

    @ConfigProperty(name = "api.secret")
    String apiSecret;

    @ConfigProperty(name = "authorization")
    String authorization;

    @Inject
    ImageClassificationPython imageClassificationPython;

    public String performClassification(final String url) {
        return imageClassificationPython.execute(apiKey, apiSecret, authorization, url);
    }

}
