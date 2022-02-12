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
import dev.nulli.banten.structure.either.Either4;
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
 * A test class used to verify the contracts of all methods within {@link Either4}.
 */
@TestImplementation(Either4.class)
public final class Either4Test {

    /**
     * An instance for random generation.
     */
    private final Random random;

    /**
     * The first type to store in an {@link Either4}.
     */
    private int firstValue;

    /**
     * The second type to store in an {@link Either4}.
     */
    private double secondValue;

    /**
     * The third type to store in an {@link Either4}.
     */
    private long thirdValue;

    /**
     * The fourth type to store in an {@link Either4}.
     */
    private float fourthValue;

    /**
     * The either instances to test.
     */
    private Either4<Integer, Double, Long, Float> first, second, third, fourth;

    /**
     * Default constructor.
     */
    private Either4Test() {
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
        this.fourthValue = this.random.nextFloat();
        this.first = Either4.first(this.firstValue);
        this.second = Either4.second(this.secondValue);
        this.third = Either4.third(this.thirdValue);
        this.fourth = Either4.fourth(this.fourthValue);
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#first(Object)}</li>
     * <li>{@link Either4#second(Object)}</li>
     * <li>{@link Either4#third(Object)}</li>
     * <li>{@link Either4#fourth(Object)}</li>
     * </ul>
     */
    @Test
    public void testInitialization() {
        // Test first values
        Assertions.assertDoesNotThrow(() -> Either4.first(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either4.first(null));

        // Test second values
        Assertions.assertDoesNotThrow(() -> Either4.second(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either4.second(null));

        // Test third values
        Assertions.assertDoesNotThrow(() -> Either4.third(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either4.third(null));

        // Test fourth values
        Assertions.assertDoesNotThrow(() -> Either4.fourth(this.fourthValue));
        Assertions.assertThrows(NullPointerException.class, () -> Either4.fourth(null));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#first()}</li>
     * <li>{@link Either4#second()}</li>
     * <li>{@link Either4#third()}</li>
     * <li>{@link Either4#fourth()}</li>
     * </ul>
     */
    @Test
    public void testValues() {
        // Test first values
        Assertions.assertEquals(this.first.first(), Optional.of(this.firstValue));
        Assertions.assertEquals(this.first.second(), Optional.empty());
        Assertions.assertEquals(this.first.third(), Optional.empty());
        Assertions.assertEquals(this.first.fourth(), Optional.empty());

        // Test second values
        Assertions.assertEquals(this.second.second(), Optional.of(this.secondValue));
        Assertions.assertEquals(this.second.first(), Optional.empty());
        Assertions.assertEquals(this.second.third(), Optional.empty());
        Assertions.assertEquals(this.second.fourth(), Optional.empty());

        // Test third values
        Assertions.assertEquals(this.third.third(), Optional.of(this.thirdValue));
        Assertions.assertEquals(this.third.first(), Optional.empty());
        Assertions.assertEquals(this.third.second(), Optional.empty());
        Assertions.assertEquals(this.third.fourth(), Optional.empty());

        // Test third values
        Assertions.assertEquals(this.fourth.fourth(), Optional.of(this.fourthValue));
        Assertions.assertEquals(this.fourth.first(), Optional.empty());
        Assertions.assertEquals(this.fourth.second(), Optional.empty());
        Assertions.assertEquals(this.fourth.third(), Optional.empty());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#firstRaw()}</li>
     * <li>{@link Either4#secondRaw()}</li>
     * <li>{@link Either4#thirdRaw()}</li>
     * <li>{@link Either4#fourthRaw()}</li>
     * </ul>
     */
    @Test
    public void testRawValues() {
        // Test first values
        Assertions.assertEquals(this.first.firstRaw(), this.firstValue);
        Assertions.assertNull(this.first.secondRaw());
        Assertions.assertNull(this.first.thirdRaw());
        Assertions.assertNull(this.first.fourthRaw());

        // Test second values
        Assertions.assertEquals(this.second.secondRaw(), this.secondValue);
        Assertions.assertNull(this.second.firstRaw());
        Assertions.assertNull(this.second.thirdRaw());
        Assertions.assertNull(this.second.fourthRaw());

        // Test third values
        Assertions.assertEquals(this.third.thirdRaw(), this.thirdValue);
        Assertions.assertNull(this.third.firstRaw());
        Assertions.assertNull(this.third.secondRaw());
        Assertions.assertNull(this.third.fourthRaw());

        // Test fourth values
        Assertions.assertEquals(this.fourth.fourthRaw(), this.fourthValue);
        Assertions.assertNull(this.fourth.firstRaw());
        Assertions.assertNull(this.fourth.secondRaw());
        Assertions.assertNull(this.fourth.thirdRaw());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#hasFirst()}</li>
     * <li>{@link Either4#hasSecond()}</li>
     * <li>{@link Either4#hasThird()}</li>
     * <li>{@link Either4#hasFourth()}</li>
     * </ul>
     */
    @Test
    public void testHasValues() {
        // Test first values
        Assertions.assertTrue(this.first.hasFirst());
        Assertions.assertFalse(this.first.hasSecond());
        Assertions.assertFalse(this.first.hasThird());
        Assertions.assertFalse(this.first.hasFourth());

        // Test second values
        Assertions.assertTrue(this.second.hasSecond());
        Assertions.assertFalse(this.second.hasFirst());
        Assertions.assertFalse(this.second.hasThird());
        Assertions.assertFalse(this.second.hasFourth());

        // Test third values
        Assertions.assertTrue(this.third.hasThird());
        Assertions.assertFalse(this.third.hasFirst());
        Assertions.assertFalse(this.third.hasSecond());
        Assertions.assertFalse(this.third.hasFourth());

        // Test fourth values
        Assertions.assertTrue(this.fourth.hasFourth());
        Assertions.assertFalse(this.fourth.hasFirst());
        Assertions.assertFalse(this.fourth.hasSecond());
        Assertions.assertFalse(this.fourth.hasThird());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#ifFirst(Consumer)}</li>
     * <li>{@link Either4#ifSecond(Consumer)}</li>
     * <li>{@link Either4#ifThird(Consumer)}</li>
     * <li>{@link Either4#ifFourth(Consumer)}</li>
     * <li>{@link Either4#ifPresent(Consumer, Consumer, Consumer, Consumer)}</li>
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
        final Consumer<Float> fourthConsumer = $ -> Assertions.assertTrue(true),
                fourthThrowingConsumer = $ -> {
                    throw new IllegalStateException("This should not happen");
                };

        // Test first values
        this.first.ifFirst(firstConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifSecond(secondThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifThird(thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifFourth(fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifFourth(null));

        Assertions.assertDoesNotThrow(() -> this.first.ifPresent(firstConsumer, secondThrowingConsumer, thirdThrowingConsumer, fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(firstConsumer, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, secondThrowingConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, null, thirdThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.ifPresent(null, null, null, fourthThrowingConsumer));

        // Test second values
        this.second.ifSecond(secondConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifFirst(firstThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifThird(thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifFourth(fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifFourth(null));

        Assertions.assertDoesNotThrow(() -> this.second.ifPresent(firstThrowingConsumer, secondConsumer, thirdThrowingConsumer, fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(firstThrowingConsumer, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, secondConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, null, thirdThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.ifPresent(null, null, null, fourthThrowingConsumer));

        // Test third values
        this.third.ifThird(thirdConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifFirst(firstThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifSecond(secondThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifFourth(fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifFourth(null));

        Assertions.assertDoesNotThrow(() -> this.third.ifPresent(firstThrowingConsumer, secondThrowingConsumer, thirdConsumer, fourthThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(firstThrowingConsumer, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, secondThrowingConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, null, thirdConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.ifPresent(null, null, null, fourthThrowingConsumer));

        // Test fourth values
        this.fourth.ifFourth(fourthConsumer);
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifFourth(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.ifFirst(firstThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifFirst(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.ifSecond(secondThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifSecond(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.ifThird(thirdThrowingConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifThird(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.ifPresent(firstThrowingConsumer, secondThrowingConsumer, thirdThrowingConsumer, fourthConsumer));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifPresent(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifPresent(firstThrowingConsumer, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifPresent(null, secondThrowingConsumer, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifPresent(null, null, thirdThrowingConsumer, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.ifPresent(null, null, null, fourthConsumer));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#mapFirst(Function)}</li>
     * <li>{@link Either4#mapSecond(Function)}</li>
     * <li>{@link Either4#mapThird(Function)}</li>
     * <li>{@link Either4#mapFourth(Function)}</li>
     * <li>{@link Either4#mapAll(Function, Function, Function, Function)}</li>
     * <li>{@link Either4#mapTo(Function, Function, Function, Function)}</li>
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
        final Function<Float, String> fourthFunction = Object::toString,
                fourthNullFunction = $ -> null;

        // Test first values
        Assertions.assertEquals(this.first.mapFirst(firstFunction).firstRaw(), String.valueOf(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapFirst(firstNullFunction));

        Assertions.assertDoesNotThrow(() -> this.first.mapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.mapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapThird(null));

        Assertions.assertDoesNotThrow(() -> this.first.mapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.mapAll(firstFunction, secondNullFunction, thirdNullFunction, fourthNullFunction).firstRaw(), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(firstFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(null, null, null, fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapAll(firstNullFunction, secondFunction, thirdFunction, fourthFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.mapTo(firstFunction, secondNullFunction, thirdNullFunction, fourthNullFunction), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(firstFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.mapTo(null, null, null, fourthNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.first.mapTo(firstNullFunction, secondFunction, thirdFunction, fourthFunction)));

        // Test second values
        Assertions.assertEquals(this.second.mapSecond(secondFunction).secondRaw(), String.valueOf(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapSecond(secondNullFunction));

        Assertions.assertDoesNotThrow(() -> this.second.mapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.mapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapThird(null));

        Assertions.assertDoesNotThrow(() -> this.second.mapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.mapAll(firstNullFunction, secondFunction, thirdNullFunction, fourthNullFunction).secondRaw(), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, secondFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(null, null, null, fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapAll(firstFunction, secondNullFunction, thirdFunction, fourthFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.mapTo(firstNullFunction, secondFunction, thirdNullFunction, fourthNullFunction), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, secondFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapTo(null, null, null, fourthNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.second.mapTo(firstFunction, secondNullFunction, thirdFunction, fourthFunction)));

        // Test third values
        Assertions.assertEquals(this.third.mapThird(thirdFunction).thirdRaw(), String.valueOf(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapThird(thirdNullFunction));

        Assertions.assertDoesNotThrow(() -> this.third.mapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.mapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.third.mapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.mapAll(firstNullFunction, secondNullFunction, thirdFunction, fourthNullFunction).thirdRaw(), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, null, thirdFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(null, null, null, fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapAll(firstFunction, secondFunction, thirdNullFunction, fourthFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.mapTo(firstNullFunction, secondNullFunction, thirdFunction, fourthNullFunction), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, null, thirdFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.mapTo(null, null, null, fourthNullFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.third.mapTo(firstFunction, secondFunction, thirdNullFunction, fourthFunction)));

        // Test fourth values
        Assertions.assertEquals(this.fourth.mapFourth(fourthFunction).fourthRaw(), String.valueOf(this.fourthValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapFourth(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapFourth(fourthNullFunction));

        Assertions.assertDoesNotThrow(() -> this.fourth.mapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.mapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.mapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.mapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.fourth.mapAll(firstNullFunction, secondNullFunction, thirdNullFunction, fourthFunction).fourthRaw(), String.valueOf(this.fourthValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(null, null, null, fourthFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapAll(firstFunction, secondFunction, thirdFunction, fourthNullFunction));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.fourth.mapTo(firstNullFunction, secondNullFunction, thirdNullFunction, fourthFunction), String.valueOf(this.fourthValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapTo(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapTo(firstNullFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapTo(null, secondNullFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapTo(null, null, thirdNullFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.mapTo(null, null, null, fourthFunction));
        Assertions.assertDoesNotThrow(() -> Assertions.assertNull(this.fourth.mapTo(firstFunction, secondFunction, thirdFunction, fourthNullFunction)));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#flatMapFirst(Function)}</li>
     * <li>{@link Either4#flatMapSecond(Function)}</li>
     * <li>{@link Either4#flatMapThird(Function)}</li>
     * <li>{@link Either4#flatMapFourth(Function)}</li>
     * <li>{@link Either4#flatMapAll(Function, Function, Function, Function)}</li>
     * </ul>
     */
    @Test
    public void testFlatMapValues() {
        // Set up initial values
        final Function<Integer, Either4<String, Double, Long, Float>> firstFunction = i -> Either4.first(Objects.toString(i)),
                firstNullFunction = $ -> null;
        final Function<Integer, Either4<String, String, String, String>> firstStringFunction = i -> Either4.first(Objects.toString(i)),
                firstNullStringFunction = $ -> null;

        final Function<Double, Either4<Integer, String, Long, Float>> secondFunction = d -> Either4.second(Objects.toString(d)),
                secondNullFunction = $ -> null;
        final Function<Double, Either4<String, String, String, String>> secondStringFunction = d -> Either4.second(Objects.toString(d)),
                secondNullStringFunction = $ -> null;

        final Function<Long, Either4<Integer, Double, String, Float>> thirdFunction = l -> Either4.third(Objects.toString(l)),
                thirdNullFunction = $ -> null;
        final Function<Long, Either4<String, String, String, String>> thirdStringFunction = l -> Either4.third(Objects.toString(l)),
                thirdNullStringFunction = $ -> null;

        final Function<Float, Either4<Integer, Double, Long, String>> fourthFunction = f -> Either4.fourth(Objects.toString(f)),
                fourthNullFunction = $ -> null;
        final Function<Float, Either4<String, String, String, String>> fourthStringFunction = f -> Either4.fourth(Objects.toString(f)),
                fourthNullStringFunction = $ -> null;

        // Test first values
        Assertions.assertEquals(this.first.flatMapFirst(firstFunction).firstRaw(), String.valueOf(this.firstValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapFirst(firstNullFunction));

        Assertions.assertDoesNotThrow(() -> this.first.flatMapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.first.flatMapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapThird(null));

        Assertions.assertDoesNotThrow(() -> this.first.flatMapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.first.flatMapAll(firstStringFunction, secondNullStringFunction, thirdNullStringFunction, fourthNullStringFunction).firstRaw(), String.valueOf(this.firstValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(firstStringFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, secondNullStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, null, thirdNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(null, null, null, fourthNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.flatMapAll(firstNullStringFunction, secondStringFunction, thirdStringFunction, fourthStringFunction));

        // Test second values
        Assertions.assertEquals(this.second.flatMapSecond(secondFunction).secondRaw(), String.valueOf(this.secondValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapSecond(secondNullFunction));

        Assertions.assertDoesNotThrow(() -> this.second.flatMapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.second.flatMapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapThird(null));

        Assertions.assertDoesNotThrow(() -> this.second.flatMapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.second.flatMapAll(firstNullStringFunction, secondStringFunction, thirdNullStringFunction, fourthNullStringFunction).secondRaw(), String.valueOf(this.secondValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(firstNullStringFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, secondStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, null, thirdNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(null, null, null, fourthNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.flatMapAll(firstStringFunction, secondNullStringFunction, thirdStringFunction, fourthStringFunction));

        // Test third values
        Assertions.assertEquals(this.third.flatMapThird(thirdFunction).thirdRaw(), String.valueOf(this.thirdValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapThird(thirdNullFunction));

        Assertions.assertDoesNotThrow(() -> this.third.flatMapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.third.flatMapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.third.flatMapFourth(fourthNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapFourth(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.third.flatMapAll(firstNullStringFunction, secondNullStringFunction, thirdStringFunction, fourthNullStringFunction).thirdRaw(), String.valueOf(this.thirdValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(firstNullStringFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, secondNullStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, null, thirdStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(null, null, null, fourthNullStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.flatMapAll(firstStringFunction, secondStringFunction, thirdNullStringFunction, fourthStringFunction));

        // Test fourth values
        Assertions.assertEquals(this.fourth.flatMapFourth(fourthFunction).fourthRaw(), String.valueOf(this.fourthValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapFourth(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapFourth(fourthNullFunction));

        Assertions.assertDoesNotThrow(() -> this.fourth.flatMapFirst(firstNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapFirst(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.flatMapSecond(secondNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapSecond(null));

        Assertions.assertDoesNotThrow(() -> this.fourth.flatMapThird(thirdNullFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapThird(null));

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertEquals(this.fourth.flatMapAll(firstNullStringFunction, secondNullStringFunction, thirdNullStringFunction, fourthStringFunction).fourthRaw(), String.valueOf(this.fourthValue)));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(null, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(firstNullStringFunction, null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(null, secondNullStringFunction, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(null, null, thirdNullStringFunction, null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(null, null, null, fourthStringFunction));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.flatMapAll(firstStringFunction, secondStringFunction, thirdStringFunction, fourthNullStringFunction));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#swapFirst()}</li>
     * <li>{@link Either4#swapSecond()}</li>
     * <li>{@link Either4#swapThird()}</li>
     * </ul>
     */
    @Test
    public void testSwap() {
        // Test first values
        Assertions.assertEquals(this.first.swapFirst().secondRaw(), this.firstValue);
        Assertions.assertNull(this.first.swapFirst().firstRaw());
        Assertions.assertNull(this.first.swapFirst().thirdRaw());
        Assertions.assertNull(this.first.swapFirst().fourthRaw());

        Assertions.assertEquals(this.first.swapSecond().firstRaw(), this.firstValue);
        Assertions.assertNull(this.first.swapSecond().secondRaw());
        Assertions.assertNull(this.first.swapSecond().thirdRaw());
        Assertions.assertNull(this.first.swapSecond().fourthRaw());

        Assertions.assertEquals(this.first.swapThird().firstRaw(), this.firstValue);
        Assertions.assertNull(this.first.swapThird().secondRaw());
        Assertions.assertNull(this.first.swapThird().thirdRaw());
        Assertions.assertNull(this.first.swapThird().fourthRaw());

        // Test second values
        Assertions.assertEquals(this.second.swapFirst().firstRaw(), this.secondValue);
        Assertions.assertNull(this.second.swapFirst().secondRaw());
        Assertions.assertNull(this.second.swapFirst().thirdRaw());
        Assertions.assertNull(this.second.swapFirst().fourthRaw());

        Assertions.assertEquals(this.second.swapSecond().thirdRaw(), this.secondValue);
        Assertions.assertNull(this.second.swapSecond().firstRaw());
        Assertions.assertNull(this.second.swapSecond().secondRaw());
        Assertions.assertNull(this.second.swapSecond().fourthRaw());

        Assertions.assertEquals(this.second.swapThird().secondRaw(), this.secondValue);
        Assertions.assertNull(this.second.swapThird().firstRaw());
        Assertions.assertNull(this.second.swapThird().thirdRaw());
        Assertions.assertNull(this.second.swapThird().fourthRaw());

        // Test third values
        Assertions.assertEquals(this.third.swapFirst().thirdRaw(), this.thirdValue);
        Assertions.assertNull(this.third.swapFirst().firstRaw());
        Assertions.assertNull(this.third.swapFirst().secondRaw());
        Assertions.assertNull(this.third.swapFirst().fourthRaw());

        Assertions.assertEquals(this.third.swapSecond().secondRaw(), this.thirdValue);
        Assertions.assertNull(this.third.swapSecond().firstRaw());
        Assertions.assertNull(this.third.swapSecond().thirdRaw());
        Assertions.assertNull(this.third.swapSecond().fourthRaw());

        Assertions.assertEquals(this.third.swapThird().fourthRaw(), this.thirdValue);
        Assertions.assertNull(this.third.swapThird().firstRaw());
        Assertions.assertNull(this.third.swapThird().secondRaw());
        Assertions.assertNull(this.third.swapThird().thirdRaw());

        // Test fourth values
        Assertions.assertEquals(this.fourth.swapFirst().fourthRaw(), this.fourthValue);
        Assertions.assertNull(this.fourth.swapFirst().firstRaw());
        Assertions.assertNull(this.fourth.swapFirst().secondRaw());
        Assertions.assertNull(this.fourth.swapFirst().thirdRaw());

        Assertions.assertEquals(this.fourth.swapSecond().fourthRaw(), this.fourthValue);
        Assertions.assertNull(this.fourth.swapSecond().firstRaw());
        Assertions.assertNull(this.fourth.swapSecond().secondRaw());
        Assertions.assertNull(this.fourth.swapSecond().thirdRaw());

        Assertions.assertEquals(this.fourth.swapThird().thirdRaw(), this.fourthValue);
        Assertions.assertNull(this.fourth.swapThird().firstRaw());
        Assertions.assertNull(this.fourth.swapThird().secondRaw());
        Assertions.assertNull(this.fourth.swapThird().fourthRaw());
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#streamFirst()}</li>
     * <li>{@link Either4#streamSecond()}</li>
     * <li>{@link Either4#streamThird()}</li>
     * <li>{@link Either4#streamFourth()}</li>
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

        Assertions.assertEquals(this.first.streamFourth().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.first.streamFourth().findFirst().isEmpty()));

        // Test second values
        Assertions.assertEquals(this.second.streamSecond().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.streamSecond().findFirst().orElseThrow(), this.secondValue));

        Assertions.assertEquals(this.second.streamFirst().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.second.streamFirst().findFirst().isEmpty()));

        Assertions.assertEquals(this.second.streamThird().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.second.streamThird().findFirst().isEmpty()));

        Assertions.assertEquals(this.second.streamFourth().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.second.streamFourth().findFirst().isEmpty()));

        // Test third values
        Assertions.assertEquals(this.third.streamThird().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.streamThird().findFirst().orElseThrow(), this.thirdValue));

        Assertions.assertEquals(this.third.streamFirst().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.third.streamFirst().findFirst().isEmpty()));

        Assertions.assertEquals(this.third.streamSecond().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.third.streamSecond().findFirst().isEmpty()));

        Assertions.assertEquals(this.third.streamFourth().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.third.streamFourth().findFirst().isEmpty()));

        // Test fourth values
        Assertions.assertEquals(this.fourth.streamFourth().count(), 1L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.streamFourth().findFirst().orElseThrow(), this.fourthValue));

        Assertions.assertEquals(this.fourth.streamFirst().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.fourth.streamFirst().findFirst().isEmpty()));

        Assertions.assertEquals(this.fourth.streamSecond().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.fourth.streamSecond().findFirst().isEmpty()));

        Assertions.assertEquals(this.fourth.streamThird().count(), 0L);
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.fourth.streamThird().findFirst().isEmpty()));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#orFirst(Supplier)}</li>
     * <li>{@link Either4#orSecond(Supplier)}</li>
     * <li>{@link Either4#orThird(Supplier)}</li>
     * <li>{@link Either4#orFourth(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testSetValue() {
        // Set up initial values
        final int firstOr = this.random.nextInt();
        final double secondOr = this.random.nextDouble();
        final long thirdOr = this.random.nextLong();
        final float fourthOr = this.random.nextFloat();

        final Supplier<Integer> firstSupplier = () -> firstOr,
                firstNullSupplier = () -> null;
        final Supplier<Double> secondSupplier = () -> secondOr,
                secondNullSupplier = () -> null;
        final Supplier<Long> thirdSupplier = () -> thirdOr,
                thirdNullSupplier = () -> null;
        final Supplier<Float> fourthSupplier = () -> fourthOr,
                fourthNullSupplier = () -> null;

        // Test first values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orFirst(firstSupplier).firstRaw(), this.firstValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orSecond(secondSupplier).secondRaw(), secondOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orThird(thirdSupplier).thirdRaw(), thirdOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orFourth(fourthSupplier).fourthRaw(), fourthOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orFirst(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.first.orFirst(firstNullSupplier).firstRaw(), this.firstValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orSecond(secondNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orThird(thirdNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.first.orFourth(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orFourth(fourthNullSupplier));

        // Test second values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orSecond(secondSupplier).secondRaw(), this.secondValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orFirst(firstSupplier).firstRaw(), firstOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orThird(thirdSupplier).thirdRaw(), thirdOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orFourth(fourthSupplier).fourthRaw(), fourthOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orSecond(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.second.orSecond(secondNullSupplier).secondRaw(), this.secondValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFirst(firstNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orThird(thirdNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFourth(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orFourth(fourthNullSupplier));

        // Test third values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orThird(thirdSupplier).thirdRaw(), this.thirdValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orFirst(firstSupplier).firstRaw(), firstOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orSecond(secondSupplier).secondRaw(), secondOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orFourth(fourthSupplier).fourthRaw(), fourthOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orThird(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.third.orThird(thirdNullSupplier).thirdRaw(), this.thirdValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFirst(firstNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orSecond(secondNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFourth(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orFourth(fourthNullSupplier));

        // Test fourth values
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orFourth(fourthSupplier).fourthRaw(), this.fourthValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orFirst(firstSupplier).firstRaw(), firstOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orSecond(secondSupplier).secondRaw(), secondOr));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orThird(thirdSupplier).thirdRaw(), thirdOr));

        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orFourth(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orFourth(fourthNullSupplier).fourthRaw(), this.fourthValue));

        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orFirst(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orFirst(firstNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orSecond(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orSecond(secondNullSupplier));

        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orThird(null));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orThird(thirdNullSupplier));
    }

    /**
     * Test for the following methods:
     *
     * <ul>
     * <li>{@link Either4#orElseFirst(Object)}</li>
     * <li>{@link Either4#orElseGetFirst(Supplier)}</li>
     * <li>{@link Either4#orElseThrowFirst()}</li>
     * <li>{@link Either4#orElseThrowFirst(Supplier)}</li>
     * <li>{@link Either4#orElseSecond(Object)}</li>
     * <li>{@link Either4#orElseGetSecond(Supplier)}</li>
     * <li>{@link Either4#orElseThrowSecond()}</li>
     * <li>{@link Either4#orElseThrowSecond(Supplier)}</li>
     * <li>{@link Either4#orElseThird(Object)}</li>
     * <li>{@link Either4#orElseGetThird(Supplier)}</li>
     * <li>{@link Either4#orElseThrowThird()}</li>
     * <li>{@link Either4#orElseThrowThird(Supplier)}</li>
     * <li>{@link Either4#orElseFourth(Object)}</li>
     * <li>{@link Either4#orElseGetFourth(Supplier)}</li>
     * <li>{@link Either4#orElseThrowFourth()}</li>
     * <li>{@link Either4#orElseThrowFourth(Supplier)}</li>
     * </ul>
     */
    @Test
    public void testAlternateValue() {
        // Set up initial values
        final int firstAlt = this.random.nextInt();
        final double secondAlt = this.random.nextDouble();
        final long thirdAlt = this.random.nextLong();
        final float fourthAlt = this.random.nextFloat();

        final Supplier<Integer> firstAltSupplier = () -> firstAlt;
        final Supplier<Double> secondAltSupplier = () -> secondAlt;
        final Supplier<Long> thirdAltSupplier = () -> thirdAlt;
        final Supplier<Float> fourthAltSupplier = () -> fourthAlt;

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

        Assertions.assertEquals(this.first.orElseFourth(fourthAlt), fourthAlt);
        Assertions.assertEquals(this.first.orElseGetFourth(fourthAltSupplier), fourthAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseGetFourth(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowFourth());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.first.orElseThrowFourth(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowFourth(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.first.orElseThrowFourth(null));

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

        Assertions.assertEquals(this.second.orElseFourth(fourthAlt), fourthAlt);
        Assertions.assertEquals(this.second.orElseGetFourth(fourthAltSupplier), fourthAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseGetFourth(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowFourth());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.second.orElseThrowFourth(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowFourth(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.second.orElseThrowFourth(null));

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

        Assertions.assertEquals(this.third.orElseFourth(fourthAlt), fourthAlt);
        Assertions.assertEquals(this.third.orElseGetFourth(fourthAltSupplier), fourthAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseGetFourth(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowFourth());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.third.orElseThrowFourth(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowFourth(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.third.orElseThrowFourth(null));

        // Test fourth values
        Assertions.assertEquals(this.fourth.orElseFourth(fourthAlt), this.fourthValue);
        Assertions.assertEquals(this.fourth.orElseGetFourth(fourthAltSupplier), this.fourthValue);
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseGetFourth(null));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orElseThrowFourth(), this.fourthValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orElseThrowFourth(throwingSupplier), this.fourthValue));
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(this.fourth.orElseThrowFourth(throwingNullSupplier), this.fourthValue));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowFourth(null));

        Assertions.assertEquals(this.fourth.orElseFirst(firstAlt), firstAlt);
        Assertions.assertEquals(this.fourth.orElseGetFirst(firstAltSupplier), firstAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseGetFirst(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowFirst());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowFirst(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowFirst(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowFirst(null));

        Assertions.assertEquals(this.fourth.orElseSecond(secondAlt), secondAlt);
        Assertions.assertEquals(this.fourth.orElseGetSecond(secondAltSupplier), secondAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseGetSecond(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowSecond());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowSecond(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowSecond(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowSecond(null));

        Assertions.assertEquals(this.fourth.orElseThird(thirdAlt), thirdAlt);
        Assertions.assertEquals(this.fourth.orElseGetThird(thirdAltSupplier), thirdAlt);
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseGetThird(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowThird());
        Assertions.assertThrows(NoSuchElementException.class, () -> this.fourth.orElseThrowThird(throwingSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowThird(throwingNullSupplier));
        Assertions.assertThrows(NullPointerException.class, () -> this.fourth.orElseThrowThird(null));
    }
}
