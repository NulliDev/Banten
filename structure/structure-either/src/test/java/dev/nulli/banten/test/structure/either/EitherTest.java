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

import dev.nulli.banten.annotation.core.test.TestImplementation;
import dev.nulli.banten.structure.either.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A test class used to verify the contracts of all methods within {@link Either}.
 */
@TestImplementation(Either.class)
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

    /**
     * Sets up initial data before each test.
     */
    @BeforeEach
    public void randomizeValues() {
        this.leftValue = this.random.nextInt();
        this.rightValue = this.random.nextDouble();
        this.left = Either.left(this.leftValue);
        this.right = Either.right(this.rightValue);
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#left(Object)}</li>
     * <li>{@link Either#right(Object)}</li>
     * </ul>
     */
    @Test
    public void testInitialization() {
        // Test left values
        Assertions.assertDoesNotThrow(() -> Either.left(this.leftValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either.left(null));

        // Test right values
        Assertions.assertDoesNotThrow(() -> Either.right(this.rightValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either.right(null));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#left()}</li>
     * <li>{@link Either#right()}</li>
     * </ul>
     */
    @Test
    public void testValues() {
        // Test left values
        Assertions.assertEquals(this.left.left(), Optional.of(this.leftValue));
        Assertions.assertEquals(this.left.right(), Optional.empty());

        // Test right values
        Assertions.assertEquals(this.right.left(), Optional.empty());
        Assertions.assertEquals(this.right.right(), Optional.of(this.rightValue));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#leftRaw()}</li>
     * <li>{@link Either#rightRaw()}</li>
     * </ul>
     */
    @Test
    public void testRawValues() {
        // Test left values
        Assertions.assertEquals(this.left.leftRaw(), this.leftValue);
        Assertions.assertNull(this.left.rightRaw());

        // Test right values
        Assertions.assertNull(this.right.leftRaw());
        Assertions.assertEquals(this.right.rightRaw(), this.rightValue);
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#hasLeft()}</li>
     * <li>{@link Either#hasRight()}</li>
     * </ul>
     */
    @Test
    public void testHasValues() {
        // Test left values
        Assertions.assertTrue(this.left.hasLeft());
        Assertions.assertFalse(this.left.hasRight());

        // Test right values
        Assertions.assertFalse(this.right.hasLeft());
        Assertions.assertTrue(this.right.hasRight());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#ifLeft(Consumer)}</li>
     * <li>{@link Either#ifRight(Consumer)}</li>
     * <li>{@link Either#ifPresent(Consumer, Consumer)}</li>
     * </ul>
     */
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

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#mapLeft(Function)}</li>
     * <li>{@link Either#mapRight(Function)}</li>
     * <li>{@link Either#mapAll(Function, Function)}</li>
     * <li>{@link Either#mapTo(Function, Function)}</li>
     * </ul>
     */
    @Test
    public void testMapValues() {
        // Set up initial values
        final Function<Integer, String> leftFunction = Object::toString,
                leftNullFunction = $ -> null;
        final Function<Double, String> rightFunction = Object::toString,
                rightNullFunction = $ -> null;

        // Test left values
        Assertions.assertEquals(this.left.mapLeft(leftFunction).leftRaw(), String.valueOf(this.leftValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapLeft(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapLeft(leftNullFunction));

        Assertions.assertDoesNotThrow(() -> this.left.mapRight(rightNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapRight(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.left.mapAll(leftFunction, rightNullFunction).leftRaw(), String.valueOf(this.leftValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapAll(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapAll(leftFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapAll(null, rightNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapAll(leftNullFunction, rightFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.left.mapTo(leftFunction, rightNullFunction), String.valueOf(this.leftValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapTo(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapTo(leftFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.mapTo(null, rightNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.left.mapTo(leftNullFunction, rightFunction)));

        // Test right values
        Assertions.assertEquals(this.right.mapRight(rightFunction).rightRaw(), String.valueOf(this.rightValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapRight(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapRight(rightNullFunction));

        Assertions.assertDoesNotThrow(() -> this.right.mapLeft(leftNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapLeft(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.right.mapAll(leftNullFunction, rightFunction).rightRaw(), String.valueOf(this.rightValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapAll(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapAll(leftNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapAll(null, rightFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapAll(leftFunction, rightNullFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.right.mapTo(leftNullFunction, rightFunction), String.valueOf(this.rightValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapTo(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapTo(leftNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.mapTo(null, rightFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.right.mapTo(leftFunction, rightNullFunction)));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#flatMapLeft(Function)}</li>
     * <li>{@link Either#flatMapRight(Function)}</li>
     * <li>{@link Either#flatMapAll(Function, Function)}</li>
     * </ul>
     */
    @Test
    public void testFlatMapValues() {
        // Set up initial values
        final Function<Integer, Either<String, Double>> leftFunction = i -> Either.left(Objects.toString(i)),
                leftNullFunction = $ -> null;
        final Function<Double, Either<Integer, String>> rightFunction = d -> Either.right(Objects.toString(d)),
                rightNullFunction = $ -> null;
        final Function<Integer, Either<String, String>> leftStringFunction = i -> Either.left(Objects.toString(i)),
                leftNullStringFunction = $ -> null;
        final Function<Double, Either<String, String>> rightStringFunction = d -> Either.right(Objects.toString(d)),
                rightNullStringFunction = $ -> null;

        // Test left values
        Assertions.assertEquals(this.left.flatMapLeft(leftFunction).leftRaw(), String.valueOf(this.leftValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapLeft(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapLeft(leftNullFunction));

        Assertions.assertDoesNotThrow(() -> this.left.flatMapRight(rightNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapRight(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.left.flatMapAll(leftStringFunction, rightNullStringFunction).leftRaw(), String.valueOf(this.leftValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapAll(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapAll(leftStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapAll(null, rightNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.flatMapAll(leftNullStringFunction, rightStringFunction));


        // Test right values
        Assertions.assertEquals(this.right.flatMapRight(rightFunction).rightRaw(), String.valueOf(this.rightValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapRight(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapRight(rightNullFunction));

        Assertions.assertDoesNotThrow(() -> this.right.flatMapLeft(leftNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapLeft(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.right.flatMapAll(leftNullStringFunction, rightStringFunction).rightRaw(), String.valueOf(this.rightValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapAll(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapAll(leftNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapAll(null, rightStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.flatMapAll(leftStringFunction, rightNullStringFunction));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#swap()}</li>
     * </ul>
     */
    @Test
    public void testSwap() {
        // Set up initial values
        final Either<Double, Integer> leftSwap = this.left.swap(),
                rightSwap = this.right.swap();

        // Test left values
        Assertions.assertEquals(leftSwap.rightRaw(), this.leftValue);
        Assertions.assertNull(leftSwap.leftRaw());

        // Test right values
        Assertions.assertEquals(rightSwap.leftRaw(), this.rightValue);
        Assertions.assertNull(rightSwap.rightRaw());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#streamLeft()}</li>
     * <li>{@link Either#streamRight()}</li>
     * </ul>
     */
    @Test
    public void testStream() {
        // Test left values
        Assertions.assertEquals(this.left.streamLeft().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.streamLeft().findFirst().orElseThrow(), this.leftValue));

        Assertions.assertEquals(this.left.streamRight().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.left.streamRight().findFirst().isEmpty()));

        // Test right values
        Assertions.assertEquals(this.right.streamRight().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.streamRight().findFirst().orElseThrow(), this.rightValue));

        Assertions.assertEquals(this.right.streamLeft().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.right.streamLeft().findFirst().isEmpty()));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#orLeft(Supplier)}</li>
     * <li>{@link Either#orRight(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testSetValue() {
        // Set up initial values
        final int leftOr = this.random.nextInt();
        final double rightOr = this.random.nextDouble();

        final Supplier<Integer> leftSupplier = () -> leftOr,
                leftNullSupplier = () -> null;
        final Supplier<Double> rightSupplier = () -> rightOr,
                rightNullSupplier = () -> null;

        // Test left values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orLeft(leftSupplier).leftRaw(), this.leftValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orRight(rightSupplier).rightRaw(), rightOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.left.orLeft(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orLeft(leftNullSupplier).leftRaw(), this.leftValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.left.orRight(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orRight(rightNullSupplier));

        // Test right values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orRight(rightSupplier).rightRaw(), this.rightValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orLeft(leftSupplier).leftRaw(), leftOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.right.orRight(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orRight(rightNullSupplier).rightRaw(), this.rightValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.right.orLeft(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orLeft(leftNullSupplier));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either#orElseLeft(Object)}</li>
     * <li>{@link Either#orElseGetLeft(Supplier)}</li>
     * <li>{@link Either#orElseThrowLeft()}</li>
     * <li>{@link Either#orElseThrowLeft(Supplier)}</li>
     * <li>{@link Either#orElseRight(Object)}</li>
     * <li>{@link Either#orElseGetRight(Supplier)}</li>
     * <li>{@link Either#orElseThrowRight()}</li>
     * <li>{@link Either#orElseThrowRight(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testAlternateValue() {
        // Set up initial values
        final int leftAlt = this.random.nextInt();
        final double rightAlt = this.random.nextDouble();

        final Supplier<Integer> leftAltSupplier = () -> leftAlt;
        final Supplier<Double> rightAltSupplier = () -> rightAlt;

        final Supplier<NoSuchElementException> throwingSupplier = NoSuchElementException::new,
                throwingNullSupplier = () -> null;

        // Test left values
        Assertions.assertEquals(this.left.orElseLeft(leftAlt), this.leftValue);
        Assertions.assertEquals(this.left.orElseGetLeft(leftAltSupplier), this.leftValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orElseGetLeft(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orElseThrowLeft(), this.leftValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orElseThrowLeft(throwingSupplier), this.leftValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.left.orElseThrowLeft(throwingNullSupplier), this.leftValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orElseThrowLeft(null));

        Assertions.assertEquals(this.left.orElseRight(rightAlt), rightAlt);
        Assertions.assertEquals(this.left.orElseGetRight(rightAltSupplier), rightAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orElseGetRight(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.left.orElseThrowRight());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.left.orElseThrowRight(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orElseThrowRight(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.left.orElseThrowRight(null));

        // Test right values
        Assertions.assertEquals(this.right.orElseRight(rightAlt), this.rightValue);
        Assertions.assertEquals(this.right.orElseGetRight(rightAltSupplier), this.rightValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orElseGetRight(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orElseThrowRight(), this.rightValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orElseThrowRight(throwingSupplier), this.rightValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.right.orElseThrowRight(throwingNullSupplier), this.rightValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orElseThrowRight(null));

        Assertions.assertEquals(this.right.orElseLeft(leftAlt), leftAlt);
        Assertions.assertEquals(this.right.orElseGetLeft(leftAltSupplier), leftAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orElseGetLeft(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.right.orElseThrowLeft());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.right.orElseThrowLeft(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orElseThrowLeft(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.right.orElseThrowLeft(null));
    }
}
