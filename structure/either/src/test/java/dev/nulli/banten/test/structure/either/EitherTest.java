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

package dev.nulli.banten.test.structure.either;

import dev.nulli.banten.annotation.core.Snapshot;
import dev.nulli.banten.annotation.core.SnapshotStage;
import dev.nulli.banten.annotation.core.TestImplementation;
import dev.nulli.banten.structure.either.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

// TODO: Finish tests, document
@TestImplementation(Either.class)
@Snapshot(SnapshotStage.ALPHA)
public final class EitherTest {

    /**
     * An instance for random generation.
     */
    private final Random random;

    /**
     * The left value to store in an {@link Either}.
     */
    private int leftValue;

    /**
     * The right value to store in an {@link Either}.
     */
    private double rightValue;

    /**
     * The either instances to test.
     */
    private Either<Integer, Double> left, right;

    /**
     * Default constructor.
     */
    private EitherTest() {
        this.random = new Random();
    }

    @BeforeEach
    public void randomizeValues() {
        this.leftValue = this.random.nextInt();
        this.rightValue = this.random.nextDouble();
        this.left = Either.left(this.leftValue);
        this.right = Either.right(this.rightValue);
    }

    // Test static left, right
    @Test
    public void testInitialization() {
        // Test left values
        Assertions.assertDoesNotThrow(() -> Either.left(this.leftValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either.left(null));

        // Test right values
        Assertions.assertDoesNotThrow(() -> Either.right(this.rightValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either.right(null));
    }

    // Test left, right
    @Test
    public void testValues() {
        // Test left values
        Assertions.assertEquals(this.left.left(), Optional.of(this.leftValue));
        Assertions.assertEquals(this.left.right(), Optional.empty());

        // Test right values
        Assertions.assertEquals(this.right.left(), Optional.empty());
        Assertions.assertEquals(this.right.right(), Optional.of(this.rightValue));
    }

    // Test leftRaw, rightRaw
    @Test
    public void testRawValues() {
        // Test left values
        Assertions.assertEquals(this.left.leftRaw(), this.leftValue);
        Assertions.assertNull(this.left.rightRaw());

        // Test right values
        Assertions.assertNull(this.right.leftRaw());
        Assertions.assertEquals(this.right.rightRaw(), this.rightValue);
    }

    // Test hasLeft, hasRight
    @Test
    public void testHasValues() {
        // Test left values
        Assertions.assertTrue(this.left.hasLeft());
        Assertions.assertFalse(this.left.hasRight());

        // Test right values
        Assertions.assertFalse(this.right.hasLeft());
        Assertions.assertTrue(this.right.hasRight());
    }

    // Test ifLeft, ifRight, ifPresent
    @Test
    public void testIfPresent() {
        // Set up initial values
        final Consumer<Integer> leftConsumer = $ -> Assertions.assertTrue(true),
                leftThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };
        final Consumer<Double> rightConsumer = $ -> Assertions.assertTrue(true),
                rightThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };

        // Test left values
        this.left.ifLeft(leftConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.left.ifLeft(null));

        Assertions.assertDoesNotThrow(() -> this.left.ifRight(rightThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.ifRight(null));

        Assertions.assertDoesNotThrow(() -> this.left.ifPresent(leftConsumer, rightThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.ifPresent(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.ifPresent(leftConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.ifPresent(null, rightThrowingConsumer));

        // Test right values
        Assertions.assertDoesNotThrow(() -> this.right.ifLeft(leftThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.ifLeft(null));

        this.right.ifRight(rightConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.right.ifRight(null));

        Assertions.assertDoesNotThrow(() -> this.right.ifPresent(leftThrowingConsumer, rightConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.ifPresent(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.ifPresent(leftThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.ifPresent(null, rightConsumer));
    }

    // Test mapLeft, mapRight, mapAll, mapTo
    @Test
    public void testMapValues() {
        // TODO: Complete
    }

    // Test flatMapLeft, flatMapRight, flatMapAll
    @Test
    public void testFlatMapValues() {
        // TODO: Complete
    }

    // Test swap
    @Test
    public void testSwap() {
        // TODO: Complete
    }

    // Test streamLeft, streamRight
    @Test
    public void testStream() {
        // TODO: Complete
    }

    // Test orLeft, orRight
    @Test
    public void testSetValue() {
        // TODO: Complete
    }

    // Test orElseLeft, orElseGetLeft, orElseThrowLeft, orElseRight, orElseGetRight, orElseThrowRight
    @Test
    public void testAlternateValue() {
        // TODO: Complete
    }
}
