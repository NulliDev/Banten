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
import dev.nulli.banten.structure.either.Either3;
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
 * A test class used to verify the contracts of all methods within {@link Either3}.
 */
@TestImplementation(Either3.class)
public final class Either3Test {

    /**
     * An instance for random generation.
     */
    private final Random random;

    /**
     * The first type to store in an {@link Either3}.
     */
    private int firstValue;

    /**
     * The second type to store in an {@link Either3}.
     */
    private double secondValue;

    /**
     * The third type to store in an {@link Either3}.
     */
    private long thirdValue;

    /**
     * The either instances to test.
     */
    private Either3<Integer, Double, Long> first, second, third;

    /**
     * Default constructor.
     */
    private Either3Test() {
        this.random = new Random();
    }

    /**
     * Sets up initial data before each test.
     */
    @BeforeEach
    public void randomizeValues() {
        this.firstValue = this.random.nextInt();
        this.secondValue = this.random.nextDouble();
        this.thirdValue = this.random.nextLong();
        this.first = Either3.first(this.firstValue);
        this.second = Either3.second(this.secondValue);
        this.third = Either3.third(this.thirdValue);
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#first(Object)}</li>
     * <li>{@link Either3#second(Object)}</li>
     * <li>{@link Either3#third(Object)}</li>
     * </ul>
     */
    @Test
    public void testInitialization() {
        // Test first values
        Assertions.assertDoesNotThrow(() -> Either3.first(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either3.first(null));

        // Test second values
        Assertions.assertDoesNotThrow(() -> Either3.second(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either3.second(null));

        // Test second values
        Assertions.assertDoesNotThrow(() -> Either3.third(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either3.third(null));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#first()}</li>
     * <li>{@link Either3#second()}</li>
     * <li>{@link Either3#third()}</li>
     * </ul>
     */
    @Test
    public void testValues() {
        // Test first values
        Assertions.assertEquals(this.first.first(), Optional.of(this.firstValue));
        Assertions.assertEquals(this.first.second(), Optional.empty());
        Assertions.assertEquals(this.first.third(), Optional.empty());

        // Test second values
        Assertions.assertEquals(this.second.second(), Optional.of(this.secondValue));
        Assertions.assertEquals(this.second.first(), Optional.empty());
        Assertions.assertEquals(this.second.third(), Optional.empty());

        // Test third values
        Assertions.assertEquals(this.third.third(), Optional.of(this.thirdValue));
        Assertions.assertEquals(this.third.first(), Optional.empty());
        Assertions.assertEquals(this.third.second(), Optional.empty());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#firstRaw()}</li>
     * <li>{@link Either3#secondRaw()}</li>
     * <li>{@link Either3#thirdRaw()}</li>
     * </ul>
     */
    @Test
    public void testRawValues() {
        // Test first values
        Assertions.assertEquals(this.first.firstRaw(), this.firstValue);
        Assertions.assertNull(this.first.secondRaw());
        Assertions.assertNull(this.first.thirdRaw());

        // Test second values
        Assertions.assertEquals(this.second.secondRaw(), this.secondValue);
        Assertions.assertNull(this.second.firstRaw());
        Assertions.assertNull(this.second.thirdRaw());

        // Test third values
        Assertions.assertEquals(this.third.thirdRaw(), this.thirdValue);
        Assertions.assertNull(this.third.firstRaw());
        Assertions.assertNull(this.third.secondRaw());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#hasFirst()}</li>
     * <li>{@link Either3#hasSecond()}</li>
     * <li>{@link Either3#hasThird()}</li>
     * </ul>
     */
    @Test
    public void testHasValues() {
        // Test first values
        Assertions.assertTrue(this.first.hasFirst());
        Assertions.assertFalse(this.first.hasSecond());
        Assertions.assertFalse(this.first.hasThird());

        // Test second values
        Assertions.assertTrue(this.second.hasSecond());
        Assertions.assertFalse(this.second.hasFirst());
        Assertions.assertFalse(this.second.hasThird());

        // Test third values
        Assertions.assertTrue(this.third.hasThird());
        Assertions.assertFalse(this.third.hasFirst());
        Assertions.assertFalse(this.third.hasSecond());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#ifFirst(Consumer)}</li>
     * <li>{@link Either3#ifSecond(Consumer)}</li>
     * <li>{@link Either3#ifThird(Consumer)}</li>
     * <li>{@link Either3#ifPresent(Consumer, Consumer, Consumer)}</li>
     * </ul>
     */
    @Test
    public void testIfPresent() {
        // Set up initial values
        final Consumer<Integer> firstConsumer = $ -> Assertions.assertTrue(true),
                firstThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };
        final Consumer<Double> secondConsumer = $ -> Assertions.assertTrue(true),
                secondThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };
        final Consumer<Long> thirdConsumer = $ -> Assertions.assertTrue(true),
                thirdThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };

        // Test first values
        this.first.ifFirst(firstConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifSecond(secondThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifThird(thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifPresent(firstConsumer, secondThrowingConsumer, thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(firstConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, secondThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, null, thirdThrowingConsumer));

        // Test second values
        this.second.ifSecond(secondConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifFirst(firstThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifThird(thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifPresent(firstThrowingConsumer, secondConsumer, thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(firstThrowingConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, secondConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, null, thirdThrowingConsumer));

        // Test third values
        this.third.ifThird(thirdConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifFirst(firstThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifSecond(secondThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifPresent(firstThrowingConsumer, secondThrowingConsumer, thirdConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(firstThrowingConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, secondThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, null, thirdConsumer));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#mapFirst(Function)}</li>
     * <li>{@link Either3#mapSecond(Function)}</li>
     * <li>{@link Either3#mapThird(Function)}</li>
     * <li>{@link Either3#mapAll(Function, Function, Function)}</li>
     * <li>{@link Either3#mapTo(Function, Function, Function)}</li>
     * </ul>
     */
    @Test
    public void testMapValues() {
        // Set up initial values
        final Function<Integer, String> firstFunction = Object::toString,
                firstNullFunction = $ -> null;
        final Function<Double, String> secondFunction = Object::toString,
                secondNullFunction = $ -> null;
        final Function<Long, String> thirdFunction = Object::toString,
                thirdNullFunction = $ -> null;

        // Test first values
        Assertions.assertEquals(this.first.mapFirst(firstFunction).firstRaw(), String.valueOf(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapFirst(firstNullFunction));

        Assertions.assertDoesNotThrow(() -> this.first.mapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.mapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.mapAll(firstFunction, secondNullFunction, thirdNullFunction).firstRaw(), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(firstFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, secondNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, null, thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(firstNullFunction, secondFunction, thirdFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.mapTo(firstFunction, secondNullFunction, thirdNullFunction), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(firstFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, secondNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, null, thirdNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.first.mapTo(firstNullFunction, secondFunction, thirdFunction)));

        // Test second values
        Assertions.assertEquals(this.second.mapSecond(secondFunction).secondRaw(), String.valueOf(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapSecond(secondNullFunction));

        Assertions.assertDoesNotThrow(() -> this.second.mapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.mapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.mapAll(firstNullFunction, secondFunction, thirdNullFunction).secondRaw(), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(firstNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, secondFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, null, thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(firstFunction, secondNullFunction, thirdFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.mapTo(firstNullFunction, secondFunction, thirdNullFunction), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(firstNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, secondFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, null, thirdNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.second.mapTo(firstFunction, secondNullFunction, thirdFunction)));

        // Test third values
        Assertions.assertEquals(this.third.mapThird(thirdFunction).thirdRaw(), String.valueOf(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapThird(thirdNullFunction));

        Assertions.assertDoesNotThrow(() -> this.third.mapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.mapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapSecond(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.mapAll(firstNullFunction, secondNullFunction, thirdFunction).thirdRaw(), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(firstNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, secondNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, null, thirdFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(firstFunction, secondFunction, thirdNullFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.mapTo(firstNullFunction, secondNullFunction, thirdFunction), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(firstNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, secondNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, null, thirdFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.third.mapTo(firstFunction, secondFunction, thirdNullFunction)));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#flatMapFirst(Function)}</li>
     * <li>{@link Either3#flatMapSecond(Function)}</li>
     * <li>{@link Either3#flatMapThird(Function)}</li>
     * <li>{@link Either3#flatMapAll(Function, Function, Function)}</li>
     * </ul>
     */
    @Test
    public void testFlatMapValues() {
        // Set up initial values
        final Function<Integer, Either3<String, Double, Long>> firstFunction = i -> Either3.first(Objects.toString(i)),
                firstNullFunction = $ -> null;
        final Function<Integer, Either3<String, String, String>> firstStringFunction = i -> Either3.first(Objects.toString(i)),
                firstNullStringFunction = $ -> null;

        final Function<Double, Either3<Integer, String, Long>> secondFunction = d -> Either3.second(Objects.toString(d)),
                secondNullFunction = $ -> null;
        final Function<Double, Either3<String, String, String>> secondStringFunction = d -> Either3.second(Objects.toString(d)),
                secondNullStringFunction = $ -> null;

        final Function<Long, Either3<Integer, Double, String>> thirdFunction = l -> Either3.third(Objects.toString(l)),
                thirdNullFunction = $ -> null;
        final Function<Long, Either3<String, String, String>> thirdStringFunction = l -> Either3.third(Objects.toString(l)),
                thirdNullStringFunction = $ -> null;

        // Test first values
        Assertions.assertEquals(this.first.flatMapFirst(firstFunction).firstRaw(), String.valueOf(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapFirst(firstNullFunction));

        Assertions.assertDoesNotThrow(() -> this.first.flatMapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.flatMapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.flatMapAll(firstStringFunction, secondNullStringFunction, thirdNullStringFunction).firstRaw(), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(firstStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, secondNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, null, thirdNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(firstNullStringFunction, secondStringFunction, thirdStringFunction));

        // Test second values
        Assertions.assertEquals(this.second.flatMapSecond(secondFunction).secondRaw(), String.valueOf(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapSecond(secondNullFunction));

        Assertions.assertDoesNotThrow(() -> this.second.flatMapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.flatMapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.flatMapAll(firstNullStringFunction, secondStringFunction, thirdNullStringFunction).secondRaw(), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(firstNullStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, secondStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, null, thirdNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(firstStringFunction, secondNullStringFunction, thirdStringFunction));

        // Test third values
        Assertions.assertEquals(this.third.flatMapThird(thirdFunction).thirdRaw(), String.valueOf(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapThird(thirdNullFunction));

        Assertions.assertDoesNotThrow(() -> this.third.flatMapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.flatMapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapSecond(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.flatMapAll(firstNullStringFunction, secondNullStringFunction, thirdStringFunction).thirdRaw(), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(firstNullStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, secondNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, null, thirdStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(firstStringFunction, secondStringFunction, thirdNullStringFunction));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#swapFirst()}</li>
     * <li>{@link Either3#swapSecond()}</li>
     * </ul>
     */
    @Test
    public void testSwap() {
        // Test first values
        Assertions.assertEquals(this.first.swapFirst().secondRaw(), this.firstValue);
        Assertions.assertNull(this.first.swapFirst().firstRaw());
        Assertions.assertNull(this.first.swapFirst().thirdRaw());

        Assertions.assertEquals(this.first.swapSecond().firstRaw(), this.firstValue);
        Assertions.assertNull(this.first.swapSecond().secondRaw());
        Assertions.assertNull(this.first.swapSecond().thirdRaw());

        // Test second values
        Assertions.assertEquals(this.second.swapFirst().firstRaw(), this.secondValue);
        Assertions.assertNull(this.second.swapFirst().secondRaw());
        Assertions.assertNull(this.second.swapFirst().thirdRaw());

        Assertions.assertEquals(this.second.swapSecond().thirdRaw(), this.secondValue);
        Assertions.assertNull(this.second.swapSecond().firstRaw());
        Assertions.assertNull(this.second.swapSecond().secondRaw());

        // Test third values
        Assertions.assertEquals(this.third.swapFirst().thirdRaw(), this.thirdValue);
        Assertions.assertNull(this.third.swapFirst().firstRaw());
        Assertions.assertNull(this.third.swapFirst().secondRaw());

        Assertions.assertEquals(this.third.swapSecond().secondRaw(), this.thirdValue);
        Assertions.assertNull(this.third.swapSecond().firstRaw());
        Assertions.assertNull(this.third.swapSecond().thirdRaw());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#streamFirst()}</li>
     * <li>{@link Either3#streamSecond()}</li>
     * <li>{@link Either3#streamThird()}</li>
     * </ul>
     */
    @Test
    public void testStream() {
        // Test first values
        Assertions.assertEquals(this.first.streamFirst().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.streamFirst().findFirst().orElseThrow(), this.firstValue));

        Assertions.assertEquals(this.first.streamSecond().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.first.streamSecond().findFirst().isEmpty()));

        Assertions.assertEquals(this.first.streamThird().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.first.streamThird().findFirst().isEmpty()));

        // Test second values
        Assertions.assertEquals(this.second.streamSecond().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.streamSecond().findFirst().orElseThrow(), this.secondValue));

        Assertions.assertEquals(this.second.streamFirst().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.second.streamFirst().findFirst().isEmpty()));

        Assertions.assertEquals(this.second.streamThird().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.second.streamThird().findFirst().isEmpty()));

        // Test third values
        Assertions.assertEquals(this.third.streamThird().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.streamThird().findFirst().orElseThrow(), this.thirdValue));

        Assertions.assertEquals(this.third.streamFirst().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.third.streamFirst().findFirst().isEmpty()));

        Assertions.assertEquals(this.third.streamSecond().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.third.streamSecond().findFirst().isEmpty()));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#orFirst(Supplier)}</li>
     * <li>{@link Either3#orSecond(Supplier)}</li>
     * <li>{@link Either3#orThird(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testSetValue() {
        // Set up initial values
        final int firstOr = this.random.nextInt();
        final double secondOr = this.random.nextDouble();
        final long thirdOr = this.random.nextLong();

        final Supplier<Integer> firstSupplier = () -> firstOr,
                firstNullSupplier = () -> null;
        final Supplier<Double> secondSupplier = () -> secondOr,
                secondNullSupplier = () -> null;
        final Supplier<Long> thirdSupplier = () -> thirdOr,
                thirdNullSupplier = () -> null;

        // Test first values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orFirst(firstSupplier).firstRaw(), this.firstValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orSecond(secondSupplier).secondRaw(), secondOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orThird(thirdSupplier).thirdRaw(), thirdOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orFirst(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orFirst(firstNullSupplier).firstRaw(), this.firstValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orSecond(secondNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orThird(thirdNullSupplier));

        // Test second values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orSecond(secondSupplier).secondRaw(), this.secondValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orFirst(firstSupplier).firstRaw(), firstOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orThird(thirdSupplier).thirdRaw(), thirdOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orSecond(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orSecond(secondNullSupplier).secondRaw(), this.secondValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFirst(firstNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orThird(thirdNullSupplier));

        // Test third values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orThird(thirdSupplier).thirdRaw(), this.thirdValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orFirst(firstSupplier).firstRaw(), firstOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orSecond(secondSupplier).secondRaw(), secondOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orThird(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orThird(thirdNullSupplier).thirdRaw(), this.thirdValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFirst(firstNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orSecond(secondNullSupplier));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either3#orElseFirst(Object)}</li>
     * <li>{@link Either3#orElseGetFirst(Supplier)}</li>
     * <li>{@link Either3#orElseThrowFirst()}</li>
     * <li>{@link Either3#orElseThrowFirst(Supplier)}</li>
     * <li>{@link Either3#orElseSecond(Object)}</li>
     * <li>{@link Either3#orElseGetSecond(Supplier)}</li>
     * <li>{@link Either3#orElseThrowSecond()}</li>
     * <li>{@link Either3#orElseThrowSecond(Supplier)}</li>
     * <li>{@link Either3#orElseThird(Object)}</li>
     * <li>{@link Either3#orElseGetThird(Supplier)}</li>
     * <li>{@link Either3#orElseThrowThird()}</li>
     * <li>{@link Either3#orElseThrowThird(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testAlternateValue() {
        // Set up initial values
        final int firstAlt = this.random.nextInt();
        final double secondAlt = this.random.nextDouble();
        final long thirdAlt = this.random.nextLong();

        final Supplier<Integer> firstAltSupplier = () -> firstAlt;
        final Supplier<Double> secondAltSupplier = () -> secondAlt;
        final Supplier<Long> thirdAltSupplier = () -> thirdAlt;

        final Supplier<NoSuchElementException> throwingSupplier = NoSuchElementException::new,
                throwingNullSupplier = () -> null;

        // Test first values
        Assertions.assertEquals(this.first.orElseFirst(firstAlt), this.firstValue);
        Assertions.assertEquals(this.first.orElseGetFirst(firstAltSupplier), this.firstValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseGetFirst(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orElseThrowFirst(), this.firstValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orElseThrowFirst(throwingSupplier), this.firstValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orElseThrowFirst(throwingNullSupplier), this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowFirst(null));

        Assertions.assertEquals(this.first.orElseSecond(secondAlt), secondAlt);
        Assertions.assertEquals(this.first.orElseGetSecond(secondAltSupplier), secondAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseGetSecond(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowSecond());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowSecond(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowSecond(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowSecond(null));

        Assertions.assertEquals(this.first.orElseThird(thirdAlt), thirdAlt);
        Assertions.assertEquals(this.first.orElseGetThird(thirdAltSupplier), thirdAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseGetThird(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowThird());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowThird(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowThird(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowThird(null));

        // Test second values
        Assertions.assertEquals(this.second.orElseSecond(secondAlt), this.secondValue);
        Assertions.assertEquals(this.second.orElseGetSecond(secondAltSupplier), this.secondValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseGetSecond(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orElseThrowSecond(), this.secondValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orElseThrowSecond(throwingSupplier), this.secondValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orElseThrowSecond(throwingNullSupplier), this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowSecond(null));

        Assertions.assertEquals(this.second.orElseFirst(firstAlt), firstAlt);
        Assertions.assertEquals(this.second.orElseGetFirst(firstAltSupplier), firstAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseGetFirst(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowFirst());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowFirst(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowFirst(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowFirst(null));

        Assertions.assertEquals(this.second.orElseThird(thirdAlt), thirdAlt);
        Assertions.assertEquals(this.second.orElseGetThird(thirdAltSupplier), thirdAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseGetThird(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowThird());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowThird(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowThird(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowThird(null));

        // Test third values
        Assertions.assertEquals(this.third.orElseThird(thirdAlt), this.thirdValue);
        Assertions.assertEquals(this.third.orElseGetThird(thirdAltSupplier), this.thirdValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseGetThird(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orElseThrowThird(), this.thirdValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orElseThrowThird(throwingSupplier), this.thirdValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orElseThrowThird(throwingNullSupplier), this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowThird(null));

        Assertions.assertEquals(this.third.orElseFirst(firstAlt), firstAlt);
        Assertions.assertEquals(this.third.orElseGetFirst(firstAltSupplier), firstAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseGetFirst(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowFirst());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowFirst(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowFirst(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowFirst(null));

        Assertions.assertEquals(this.third.orElseSecond(secondAlt), secondAlt);
        Assertions.assertEquals(this.third.orElseGetSecond(secondAltSupplier), secondAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseGetSecond(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowSecond());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowSecond(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowSecond(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowSecond(null));
    }
}
