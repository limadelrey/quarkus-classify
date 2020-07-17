package org.example.ai.classification;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.IOException;
import java.net.URL;

public enum ImageClassificationPython {
    TAGS("tags.py");

    private final Value function;

    ImageClassificationPython(final String resource) {
        try {
            final URL pythonSource = Thread.currentThread().getContextClassLoader().getResource(resource);
            assert pythonSource != null;

            final Source source = Source.newBuilder("python", pythonSource).build();
            final Context context = Context.newBuilder("python").allowAllAccess(true).build();
            context.eval(source);
            this.function = context.getBindings("python").getMember(resource.replace(".py", ""));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String execute(final Object... args) {
        return function.execute(args).asString();
    }
}
