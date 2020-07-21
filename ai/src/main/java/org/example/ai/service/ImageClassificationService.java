package org.example.ai.service;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.example.ai.model.entity.ClassificationTag;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ImageClassificationService {

    private static final String PYTHON = "python";
    private static final String PYTHON_FILE = "tags.py";
    public static final String PYTHON_METHOD_NAME = "get_random_tags";

    private Context context;
    private URL pythonFile;

    void onStart(@Observes StartupEvent event) throws IOException {
        context = Context.newBuilder().allowAllAccess(true).build();
        pythonFile = getClass().getClassLoader().getResource(PYTHON_FILE);

        context.eval(Source.newBuilder(PYTHON, pythonFile).build());
    }

    public List<ClassificationTag> execute() {
        final Value pythonMethod = context.getBindings(PYTHON).getMember(PYTHON_METHOD_NAME);
        final Value pythonReturn = pythonMethod.execute();

        // Simulate error
        // return Collections.emptyList();

        // Simulate success
        // return getStaticListWithClassificationTags();

        return new JsonArray(pythonReturn.asString())
                .stream()
                .map(JsonObject.class::cast)
                .map(document -> new ClassificationTag(document.getString("tag"), document.getDouble("confidence")))
                .collect(Collectors.toList());
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
