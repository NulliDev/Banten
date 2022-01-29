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

/**
 * The constants of this class provide some lifecycles an element annotated with
 * {@link Snapshot} can possess.
 *
 * @since 1.0.0
 * @author Aaron Haim
 */
public final class SnapshotStage {

    /**
     * Prohibited default constructor.
     */
    private SnapshotStage() {
        throw new IllegalStateException("SnapshotStage should not be instantiated");
    }

    /**
     * Element has not been completed yet.
     */
    public static final String IN_DEVELOPMENT = "IN DEVELOPMENT";

    /**
     * Elements are highly unstable and will most likely change.
     */
    public static final String ALPHA = "ALPHA";

    /**
     * Elements are in a stable enough state to be used; however they may
     * still be changed.
     */
    public static final String BETA = "BETA";

    /**
     * Elements are considered stable and unlikely to change. The element will
     * most likely be included in the next release.
     */
    public static final String RELEASE_CANDIDATE = "RELEASE CANDIDATE";
}
