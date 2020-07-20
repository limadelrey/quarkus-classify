package org.example.ai.service;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import org.example.ai.model.entity.ClassificationTag;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Traced
@ApplicationScoped
public class ImageClassificationService {

    private static final String PYTHON = "python";
    private static final String PYTHON_FILE = "tags.py";
    public static final String PYTHON_METHOD_NAME = "tags";

    @ConfigProperty(name = "api.key")
    String apiKey;

    @ConfigProperty(name = "api.secret")
    String apiSecret;

    @ConfigProperty(name = "api.auth")
    String apiAuth;

    private Context context;
    private static boolean effectPyLoaded = false;

    private Engine engine;

    void onStart(@Observes StartupEvent event) {
        engine = Engine.create();
        context = Context.newBuilder().engine(engine).allowCreateThread(true).allowNativeAccess(true).allowIO(true).allowAllAccess(true).build();
    }

    public List<ClassificationTag> execute(String url) {
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
        final String tagsResult = tagsMethod.execute(url).asString();

        // Simulate error
        // return Collections.emptyList();

        // Simulate success
/*        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return getStaticListWithClassificationTags();
    }

    /**
     * Get static list w/ classifications (useful for testing UI service)
     *
     * @return List w/ classifications
     */
    private List<ClassificationTag> getStaticListWithClassificationTags() {
        return List.of(
                new ClassificationTag(
                        "mountain",
                        61.4116096496582
                ),
                new ClassificationTag(
                        "landscape",
                        54.3507270812988
                ),
                new ClassificationTag(
                        "mountains",
                        50.969783782959
                ),
                new ClassificationTag(
                        "wall",
                        46.1385192871094
                ),
                new ClassificationTag(
                        "clouds",
                        40.6059913635254
                ),
                new ClassificationTag(
                        "sky",
                        37.2282066345215
                )
        );
    }

}
