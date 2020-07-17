package org.example.ai.classification;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URL;

@ApplicationScoped
public class ImageClassificationPython {

    private static final String PYTHON = "python";
    private static final String PYTHON_FILE = "tags.py";
    public static final String PYTHON_METHOD_NAME = "tags";

    private static Context context = Context.newBuilder().allowIO(true).allowAllAccess(true).build();
    private static boolean effectPyLoaded = false;

    public String execute(String apiKey,
                          String apiSecret,
                          String authorization,
                          String url) {
        if (!effectPyLoaded) {
            final URL pythonFile = getClass().getClassLoader().getResource(PYTHON_FILE);

            try {
                context.eval(Source.newBuilder(PYTHON, pythonFile).build());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            effectPyLoaded = true;
            System.out.println(pythonFile);
        }

        final Value tagsMethod = context.getBindings(PYTHON).getMember(PYTHON_METHOD_NAME);
        final Value tagsResult = tagsMethod.execute(apiKey, apiSecret, authorization, url);

        return tagsResult.asString();
    }

}
