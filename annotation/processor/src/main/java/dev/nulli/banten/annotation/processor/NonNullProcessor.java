package dev.nulli.banten.annotation.processor;

import dev.nulli.banten.annotation.core.development.Snapshot;
import dev.nulli.banten.annotation.core.development.SnapshotStage;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@Snapshot(SnapshotStage.IN_DEVELOPMENT)
@SupportedAnnotationTypes("dev.nulli.banten.annotation.core.NonNull")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class NonNullProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            annotations.stream().map(roundEnv::getElementsAnnotatedWith)
                    .flatMap(Set::stream).forEach(element -> {
                        //TODO: Need to figure out how to implement
                        /*
                         * Fields:
                         * - When final, just need to check during class load
                         * - When non-final, need to check any time the field is
                         * set
                         *
                         * Methods:
                         * - Need to check return result before giving back to the
                         * caller
                         *
                         * Parameter:
                         * - When final, just need to check at the start of the
                         * method
                         * - When non-final, need to check any time the parameter
                         * is set
                         *
                         * Local Variable:
                         * - When final, just need to check initial value.
                         * - When non-final, need to check any time the variable
                         * is set
                         *
                         * Record Component:
                         * - Components are implicitly final, just need to check
                         * when initialized
                         */
                    });
        }
        return true;
    }
}
