/*
 * MIT License
 *
 * Copyright (c) 2022-2022 Aaron Haim.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.nulli.banten.annotation.processor;

import dev.nulli.banten.annotation.core.Snapshot;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.Set;

/**
 * A processor which produces a warning message within the compiler if an element
 * is annotated with {@link Snapshot}.
 *
 * @apiNote
 * This message can be skipped if {@link SuppressWarnings} is also annotated on
 * the element with the {@code snapshot} value.
 *
 * @since 1.0.0
 * @author Aaron Haim
 */
@SupportedAnnotationTypes("dev.nulli.banten.annotation.core.Snapshot")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SnapshotProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            annotations.stream().map(roundEnv::getElementsAnnotatedWith)
                    .flatMap(Set::stream).forEach(element -> {
                        Snapshot snapshot = element.getAnnotation(Snapshot.class);
                        SuppressWarnings warnings = element.getAnnotation(SuppressWarnings.class);
                        if (warnings == null || Arrays.stream(warnings.value()).noneMatch(s -> s.equals("snapshot")))
                            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                                    "'" + element.getSimpleName() + "' is a snapshot element in the '" + snapshot.value() + "' phase. This element may be changed or removed in a future release.",
                                    element);
                    });
        }
        return true;
    }
}
