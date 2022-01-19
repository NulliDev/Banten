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

package dev.nulli.banten.annotation.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An accessible element (class, method, field, etc.) annotated with
 * {@code @Snapshot} may have changes that are incompatible or removed in a
 * future version.
 *
 * The lifecycle stage the element is currently part of can be specified via
 * the {@code value}. By default, this is set to {@link SnapshotStage#BETA}.
 *
 * @implNote
 * When using the annotation processor, a warning message will appear denoting
 * that the annotated element is incomplete. This message can be suppressed if
 * the element is also annotated with {@link SuppressWarnings} with the
 * {@code snapshot} value.
 *
 * @apiNote
 * Elements that are annotated with {@code @Snapshot} and will be removed or
 * moved to a different location should have a buffer beta version. The element
 * should be annotated with {@code @Deprecated} and specified in the javadoc
 * what is happening with the element.
 *
 * @since 1.0.0
 * @author Aaron Haim
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE
})
public @interface Snapshot {

    /**
     * Returns the stage the snapshot is in.
     *
     * @return the stage the snapshot is in
     */
    String value() default SnapshotStage.BETA;
}
