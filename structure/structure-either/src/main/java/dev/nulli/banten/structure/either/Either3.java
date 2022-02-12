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

package dev.nulli.banten.structure.either;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A container object which can hold one of the three types defined. If one value
 * type is present, then the other value types will not be. If the first value
 * type is present, {@link #hasFirst()} returns {@code true}. If the second value
 * is present, {@link #hasSecond()} returns {@code true}, and so on.
 *
 * <p>Additional methods that depend on the presence or absence of the value types
 * are provided, such as {@link #orElseFirst(Object)}, {@link #orElseSecond(Object)},
 * and so on, which return a default value if the associated value type is not
 * present, and {@link #ifFirst(Consumer)}, {@link #ifSecond(Consumer)}, and so on,
 * which performs an action if the associated value type is present.
 *
 * @apiNote
 * {@code Either3} is primarily intended for use when a value can be represented
 * or used as multiple types. A value represented should never itself be
 * {@code null}.
 *
 * @param <T1> the first type of the value
 * @param <T2> the second type of the value
 * @param <T3> the third type of the value
 *
 * @since 1.0.0
 * @author Aaron Haim
 */
public abstract sealed class Either3<T1, T2, T3> permits Either3.First, Either3.Second, Either3.Third {

    /**
     * Returns an {@code Either3} with the non-{@code null} first type present.
     *
     * @param value the first type value, which must be non-{@code null}
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     * @return an {@code Either3} with the first type value present
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <T1, T2, T3> Either3<T1, T2, T3> first(final T1 value) {
        return new First<>(value);
    }

    /**
     * Returns an {@code Either3} with the non-{@code null} second type present.
     *
     * @param value the second type value, which must be non-{@code null}
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     * @return an {@code Either3} with the second type value present
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <T1, T2, T3> Either3<T1, T2, T3> second(final T2 value) {
        return new Second<>(value);
    }

    /**
     * Returns an {@code Either3} with the non-{@code null} third type present.
     *
     * @param value the third type value, which must be non-{@code null}
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     * @return an {@code Either3} with the third type value present
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <T1, T2, T3> Either3<T1, T2, T3> third(final T3 value) {
        return new Third<>(value);
    }

    /**
     * If the first type value is present, returns an {@link Optional} describing the
     * first type value, otherwise an empty {@link Optional}.
     *
     * @return an {@link Optional} describing the first type value if present, otherwise
     *         an empty {@link Optional}
     */
    public abstract Optional<T1> first();

    /**
     * If the second type value is present, returns an {@link Optional} describing the
     * second type value, otherwise an empty {@link Optional}.
     *
     * @return an {@link Optional} describing the second type value if present, otherwise
     *         an empty {@link Optional}
     */
    public abstract Optional<T2> second();

    /**
     * If the third type value is present, returns an {@link Optional} describing the
     * third type value, otherwise an empty {@link Optional}.
     *
     * @return an {@link Optional} describing the third type value if present, otherwise
     *         an empty {@link Optional}
     */
    public abstract Optional<T3> third();

    /**
     * If the first type value is present, returns the value, otherwise {@code null}.
     *
     * @return the first type value if present, otherwise {@code null}
     */
    public abstract T1 firstRaw();

    /**
     * If the second type value is present, returns the value, otherwise {@code null}.
     *
     * @return the second type value if present, otherwise {@code null}
     */
    public abstract T2 secondRaw();

    /**
     * If the third type value is present, returns the value, otherwise {@code null}.
     *
     * @return the third type value if present, otherwise {@code null}
     */
    public abstract T3 thirdRaw();

    /**
     * If the first type value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if the first type value is present, otherwise {@code false}
     */
    public abstract boolean hasFirst();

    /**
     * If the second type value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if the second type value is present, otherwise {@code false}
     */
    public abstract boolean hasSecond();

    /**
     * If the third type value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if the third type value is present, otherwise {@code false}
     */
    public abstract boolean hasThird();

    /**
     * If the first type value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if the first type value is present
     * @throws NullPointerException if the given action is {@code null}
     */
    public void ifFirst(final Consumer<? super T1> action) {
        this.ifPresent(action, $ -> {}, $ -> {});
    }

    /**
     * If the second type value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if the second type value is present
     * @throws NullPointerException if the given action is {@code null}
     */
    public void ifSecond(final Consumer<? super T2> action)  {
        this.ifPresent($ -> {}, action, $ -> {});
    }

    /**
     * If the third type value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if the third type value is present
     * @throws NullPointerException if the given action is {@code null}
     */
    public void ifThird(final Consumer<? super T3> action)  {
        this.ifPresent($ -> {}, $ -> {}, action);
    }

    /**
     * Performs one of the given actions depending on which value is present.
     *
     * @param firstAction the action to be performed, if the first type value is present
     * @param secondAction the action to be performed, if the second type value is present
     * @param thirdAction the action to be performed, if the third type value is present
     * @throws NullPointerException if either action is {@code null}
     */
    public abstract void ifPresent(final Consumer<? super T1> firstAction, final Consumer<? super T2> secondAction, final Consumer<? super T3> thirdAction);

    /**
     * If the first type value is present, returns an {@code Either3} describing the
     * result of applying the given mapping function to the first type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the first type value, if present
     * @param <V> the type of the first type value returned from the mapping function
     * @return an {@code Either3} describing the result of applying a mapping
     *         function to the first type value, if present, otherwise an {@code Either3}
     *         with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<V, T2, T3> mapFirst(final Function<? super T1, ? extends V> map) {
        return mapAll(map, Function.identity(), Function.identity());
    }

    /**
     * If the second type value is present, returns an {@code Either3} describing the
     * result of applying the given mapping function to the second type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the second type value, if present
     * @param <V> the type of the second type value returned from the mapping function
     * @return an {@code Either3} describing the result of applying a mapping
     *         function to the second type value, if present, otherwise an {@code Either3}
     *         with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<T1, V, T3> mapSecond(final Function<? super T2, ? extends V> map) {
        return mapAll(Function.identity(), map, Function.identity());
    }

    /**
     * If the third type value is present, returns an {@code Either3} describing the
     * result of applying the given mapping function to the third type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the third type value, if present
     * @param <V> the type of the third type value returned from the mapping function
     * @return an {@code Either3} describing the result of applying a mapping
     *         function to the third type value, if present, otherwise an {@code Either3}
     *         with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<T1, T2, V> mapThird(final Function<? super T3, ? extends V> map) {
        return mapAll(Function.identity(), Function.identity(), map);
    }

    /**
     * Returns an {@code Either3} describing the result of applying the given
     * mapping function to the value that is present.
     *
     * @param firstMap the mapping function to apply to the first type value, if present
     * @param secondMap the mapping function to apply to the second type value, if present
     * @param thirdMap the mapping function to apply to the third type value, if present
     * @param <V1> the type of the first type value returned from the mapping function
     * @param <V2> the type of the second type value returned from the mapping function
     * @param <V3> the type of the third type value returned from the mapping function
     * @return an {@code Either3} describing the result of applying a mapping function
     *         to the present value
     * @throws NullPointerException if either mapping functions or the results of
     *                              the mapping functions are {@code null}
     */
    public abstract <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap,
                                                            final Function<? super T2, ? extends V2> secondMap,
                                                            final Function<? super T3, ? extends V3> thirdMap);

    /**
     * Returns a value describing the result of applying the given mapping function
     * to the value that is present.
     *
     * @param firstMap the mapping function to apply to the first type value, if present
     * @param secondMap the mapping function to apply to the second type value, if present
     * @param thirdMap the mapping function to apply to the third type value, if present
     * @param <V> the type of the value returned from the mapping function
     * @return a value from the result of applying a mapping function to the present
     *         value
     * @throws NullPointerException if either mapping functions are {@code null}
     */
    public abstract <V> V mapTo(final Function<? super T1, ? extends V> firstMap,
                                final Function<? super T2, ? extends V> secondMap,
                                final Function<? super T3, ? extends V> thirdMap);

    /**
     * If the first type value is present, returns the result of applying the given
     * {@code Either3}-bearing mapping function to the first type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the first type value, if present
     * @param <V> the type of the first type value of the {@code Either3} returned by
     *            the mapping function
     * @return the result of apply an {@code Either3}-bearing mapping function to
     *         the first type value of this {@code Either3}, if present, otherwise an
     *         {@code Either} with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<V, T2, T3> flatMapFirst(final Function<? super T1, ? extends Either3<? extends V, ? extends T2, ? extends T3>> map) {
        return flatMapAll(map, Either3::second, Either3::third);
    }

    /**
     * If the second type value is present, returns the result of applying the given
     * {@code Either3}-bearing mapping function to the second type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the second type value, if present
     * @param <V> the type of the second type value of the {@code Either3} returned by
     *            the mapping function
     * @return the result of apply an {@code Either3}-bearing mapping function to
     *         the second type value of this {@code Either3}, if present, otherwise an
     *         {@code Either} with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<T1, V, T3> flatMapSecond(final Function<? super T2, ? extends Either3<? extends T1, ? extends V, ? extends T3>> map) {
        return flatMapAll(Either3::first, map, Either3::third);
    }

    /**
     * If the third type value is present, returns the result of applying the given
     * {@code Either3}-bearing mapping function to the third type value, otherwise
     * returns an {@code Either3} with the same other type values.
     *
     * @param map the mapping function to apply to the third type value, if present
     * @param <V> the type of the third type value of the {@code Either3} returned by
     *            the mapping function
     * @return the result of apply an {@code Either3}-bearing mapping function to
     *         the third type value of this {@code Either3}, if present, otherwise an
     *         {@code Either} with the same other type values
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either3<T1, T2, V> flatMapThird(final Function<? super T3, ? extends Either3<? extends T1, ? extends T2, ? extends V>> map) {
        return flatMapAll(Either3::first, Either3::second, map);
    }

    /**
     * Returns the result of applying the given {@code Either3}-bearing mapping
     * function to the value that is present.
     *
     * @param firstMap the mapping function to apply to the first type value, if present
     * @param secondMap the mapping function to apply to the second type value, if present
     * @param thirdMap the mapping function to apply to the third type value, if present
     * @param <V1> the type of the first type value of the {@code Either3} returned by the
     *             mapping function
     * @param <V2> the type of the second type value of the {@code Either3} returned by the
     *             mapping function
     * @param <V3> the type of the third type value of the {@code Either3} returned by the
     *             mapping function
     * @return the result of applying an {@code Either3}-bearing mapping function to
     *         the present value
     * @throws NullPointerException if either mapping functions or the results of
     *                              the mapping functions are {@code null}
     *
     */
    public abstract <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> firstMap,
                                                                final Function<? super T2, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> secondMap,
                                                                final Function<? super T3, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> thirdMap);

    /**
     * Swaps the first and second types of the {@code Either3} such that the
     * first type value becomes the second type value or vice versa if present.
     *
     * @return an {@code Either3} where the first type value becomes the
     *         second type value or vice versa, if present
     */
    public abstract Either3<T2, T1, T3> swapFirst();

    /**
     * Swaps the second and third types of the {@code Either3} such that the
     * second type value becomes the third type value or vice versa if present.
     *
     * @return an {@code Either3} where the second type value becomes the
     *         third type value or vice versa, if present
     */
    public abstract Either3<T1, T3, T2> swapSecond();

    /**
     * If the first type value is present, a {@link Stream} is returned containing
     * only the first type value, otherwise an empty {@link Stream} is returned.
     *
     * @return the first type {@code Either3} value as a {@link Stream}
     */
    public abstract Stream<T1> streamFirst();

    /**
     * If the second type value is present, a {@link Stream} is returned containing
     * only the second type value, otherwise an empty {@link Stream} is returned.
     *
     * @return the second type {@code Either3} value as a {@link Stream}
     */
    public abstract Stream<T2> streamSecond();

    /**
     * If the third type value is present, a {@link Stream} is returned containing
     * only the third type value, otherwise an empty {@link Stream} is returned.
     *
     * @return the third type {@code Either3} value as a {@link Stream}
     */
    public abstract Stream<T3> streamThird();

    /**
     * If the first type value is present, returns this {@code Either3}, otherwise
     * returns an {@code Either3} with the supplied first type value.
     *
     * @param supplier the supplying function that produces the first type value
     * @return this {@code Either3}, if the first type value is present, otherwise an
     *         {@code Either3} with the supplied first type value
     * @throws NullPointerException if the supplier or the first type value of the
     *                              supplier is {@code null}
     */
    public abstract Either3<T1, T2, T3> orFirst(final Supplier<? extends T1> supplier);

    /**
     * If the second type value is present, returns this {@code Either3}, otherwise
     * returns an {@code Either3} with the supplied second type value.
     *
     * @param supplier the supplying function that produces the second type value
     * @return this {@code Either3}, if the second type value is present, otherwise an
     *         {@code Either3} with the supplied second type value
     * @throws NullPointerException if the supplier or the second type value of the
     *                              supplier is {@code null}
     */
    public abstract Either3<T1, T2, T3> orSecond(final Supplier<? extends T2> supplier);

    /**
     * If the third type value is present, returns this {@code Either3}, otherwise
     * returns an {@code Either3} with the supplied third type value.
     *
     * @param supplier the supplying function that produces the third type value
     * @return this {@code Either3}, if the third type value is present, otherwise an
     *         {@code Either3} with the supplied third type value
     * @throws NullPointerException if the supplier or the third type value of the
     *                              supplier is {@code null}
     */
    public abstract Either3<T1, T2, T3> orThird(final Supplier<? extends T3> supplier);

    /**
     * If the first type value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no first type value is present
     * @return the first type value, if present, otherwise {@code other}
     */
    public abstract T1 orElseFirst(final T1 other);

    /**
     * If the first type value is present, returns the value, otherwise returns the
     * result produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the first type value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if the supplying function is {@code null}
     */
    public abstract T1 orElseGetFirst(final Supplier<? extends T1> supplier);

    /**
     * If the first type value is present, returns the value, otherwise throws
     * {@link NoSuchElementException}.
     *
     * @return the non-{@code null} first type value described by this {@code Either3}
     * @throws NoSuchElementException if no first type value is present
     */
    public abstract T1 orElseThrowFirst();

    /**
     * If the first type value is present, returns the value, otherwise throws an
     * exception produced by the exception supplying function.
     *
     * @param exceptionSupplier the supplying function that produces an exception
     *                          to be thrown
     * @param <X> the type of the exception to be thrown
     * @return the first type value, if present
     * @throws X if no first type value is present
     * @throws NullPointerException if the supplying function or the exception
     *                              from the supplier is {@code null}
     */
    public abstract <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * If the second type value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no second type value is present
     * @return the second type value, if present, otherwise {@code other}
     */
    public abstract T2 orElseSecond(final T2 other);

    /**
     * If the second type value is present, returns the value, otherwise returns the
     * result produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the second type value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if the supplying function is {@code null}
     */
    public abstract T2 orElseGetSecond(final Supplier<? extends T2> supplier);

    /**
     * If the second type value is present, returns the value, otherwise throws
     * {@link NoSuchElementException}.
     *
     * @return the non-{@code null} second type value described by this {@code Either3}
     * @throws NoSuchElementException if no second type value is present
     */
    public abstract T2 orElseThrowSecond();

    /**
     * If the second type value is present, returns the value, otherwise throws an
     * exception produced by the exception supplying function.
     *
     * @param exceptionSupplier the supplying function that produces an exception
     *                          to be thrown
     * @param <X> the type of the exception to be thrown
     * @return the second type value, if present
     * @throws X if no second type value is present
     * @throws NullPointerException if the supplying function or the exception
     *                              from the supplier is {@code null}
     */
    public abstract <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * If the third type value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no third type value is present
     * @return the third type value, if present, otherwise {@code other}
     */
    public abstract T3 orElseThird(final T3 other);

    /**
     * If the third type value is present, returns the value, otherwise returns the
     * result produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the third type value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if the supplying function is {@code null}
     */
    public abstract T3 orElseGetThird(final Supplier<? extends T3> supplier);

    /**
     * If the third type value is present, returns the value, otherwise throws
     * {@link NoSuchElementException}.
     *
     * @return the non-{@code null} third type value described by this {@code Either3}
     * @throws NoSuchElementException if no third type value is present
     */
    public abstract T3 orElseThrowThird();

    /**
     * If the third type value is present, returns the value, otherwise throws an
     * exception produced by the exception supplying function.
     *
     * @param exceptionSupplier the supplying function that produces an exception
     *                          to be thrown
     * @param <X> the type of the exception to be thrown
     * @return the third type value, if present
     * @throws X if no third type value is present
     * @throws NullPointerException if the supplying function or the exception
     *                              from the supplier is {@code null}
     */
    public abstract <X extends Throwable> T3 orElseThrowThird(final Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * A container object which can hold the first type.
     *
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     */
    protected static final class First<T1, T2, T3> extends Either3<T1, T2, T3> {
        private final T1 value;

        /**
         * Default constructor.
         *
         * @param value the left value to describe, which must be non-{@code null}
         * @throws NullPointerException if {@code value} is {@code null}
         */
        private First(final T1 value) {
            this.value = Objects.requireNonNull(value, "The first value supplied must not be null");
        }

        @Override
        public Optional<T1> first() {
            return Optional.of(this.value);
        }

        @Override
        public Optional<T2> second() {
            return Optional.empty();
        }

        @Override
        public Optional<T3> third() {
            return Optional.empty();
        }

        @Override
        public T1 firstRaw() {
            return this.value;
        }

        @Override
        public T2 secondRaw() {
            return null;
        }

        @Override
        public T3 thirdRaw() {
            return null;
        }

        @Override
        public boolean hasFirst() {
            return true;
        }

        @Override
        public boolean hasSecond() {
            return false;
        }

        @Override
        public boolean hasThird() {
            return false;
        }

        @Override
        public void ifPresent(final Consumer<? super T1> firstAction,
                              final Consumer<? super T2> secondAction,
                              final Consumer<? super T3> thirdAction) {
            Objects.requireNonNull(firstAction, "The first action provided cannot be null");
            Objects.requireNonNull(secondAction, "The second action provided cannot be null");
            Objects.requireNonNull(thirdAction, "The third action provided cannot be null");
            firstAction.accept(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap,
                                                       final Function<? super T2, ? extends V2> secondMap,
                                                       final Function<? super T3, ? extends V3> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return Either3.first(firstMap.apply(this.value));
        }

        @Override
        public <V> V mapTo(final Function<? super T1, ? extends V> firstMap,
                           final Function<? super T2, ? extends V> secondMap,
                           final Function<? super T3, ? extends V> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return firstMap.apply(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> firstMap,
                                                           final Function<? super T2, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> secondMap,
                                                           final Function<? super T3, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> thirdMap) {
            Objects.requireNonNull(firstMap, "The first flat map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second flat map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third flat map provided cannot be null");

            @SuppressWarnings("unchecked")
            final Either3<V1, V2, V3> res = (Either3<V1, V2, V3>) firstMap.apply(this.value);
            return Objects.requireNonNull(res, "The returned either cannot be null");
        }

        @Override
        public Either3<T2, T1, T3> swapFirst() {
            return Either3.second(this.value);
        }

        @Override
        public Either3<T1, T3, T2> swapSecond() {
            return Either3.first(this.value);
        }

        @Override
        public Stream<T1> streamFirst() {
            return Stream.of(this.value);
        }

        @Override
        public Stream<T2> streamSecond() {
            return Stream.empty();
        }

        @Override
        public Stream<T3> streamThird() {
            return Stream.empty();
        }

        @Override
        public Either3<T1, T2, T3> orFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return this;
        }

        @Override
        public Either3<T1, T2, T3> orSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return Either3.second(supplier.get());
        }

        @Override
        public Either3<T1, T2, T3> orThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return Either3.third(supplier.get());
        }

        @Override
        public T1 orElseFirst(final T1 other) {
            return this.value;
        }

        @Override
        public T1 orElseGetFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return this.value;
        }

        @Override
        public T1 orElseThrowFirst() {
            return this.value;
        }

        @Override
        public <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            return this.value;
        }

        @Override
        public T2 orElseSecond(final T2 other) {
            return other;
        }

        @Override
        public T2 orElseGetSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T2 orElseThrowSecond() {
            throw new NoSuchElementException("Second value not present");
        }

        @Override
        public <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public T3 orElseThird(final T3 other) {
            return other;
        }

        @Override
        public T3 orElseGetThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T3 orElseThrowThird() {
            throw new NoSuchElementException("Third value not present");
        }

        @Override
        public <X extends Throwable> T3 orElseThrowThird(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            return o instanceof Either3.First<?, ?, ?> first
                    && Objects.equals(this.value, first.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return "First[" + this.value + "]";
        }
    }

    /**
     * A container object which can hold the second type.
     *
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     */
    protected static final class Second<T1, T2, T3> extends Either3<T1, T2, T3> {
        private final T2 value;

        /**
         * Default constructor.
         *
         * @param value the left value to describe, which must be non-{@code null}
         * @throws NullPointerException if {@code value} is {@code null}
         */
        private Second(final T2 value) {
            this.value = Objects.requireNonNull(value, "The second value supplied must not be null");
        }

        @Override
        public Optional<T1> first() {
            return Optional.empty();
        }

        @Override
        public Optional<T2> second() {
            return Optional.of(this.value);
        }

        @Override
        public Optional<T3> third() {
            return Optional.empty();
        }

        @Override
        public T1 firstRaw() {
            return null;
        }

        @Override
        public T2 secondRaw() {
            return this.value;
        }

        @Override
        public T3 thirdRaw() {
            return null;
        }

        @Override
        public boolean hasFirst() {
            return false;
        }

        @Override
        public boolean hasSecond() {
            return true;
        }

        @Override
        public boolean hasThird() {
            return false;
        }

        @Override
        public void ifPresent(final Consumer<? super T1> firstAction,
                              final Consumer<? super T2> secondAction,
                              final Consumer<? super T3> thirdAction) {
            Objects.requireNonNull(firstAction, "The first action provided cannot be null");
            Objects.requireNonNull(secondAction, "The second action provided cannot be null");
            Objects.requireNonNull(thirdAction, "The third action provided cannot be null");
            secondAction.accept(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap,
                                                       final Function<? super T2, ? extends V2> secondMap,
                                                       final Function<? super T3, ? extends V3> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return Either3.second(secondMap.apply(this.value));
        }

        @Override
        public <V> V mapTo(final Function<? super T1, ? extends V> firstMap,
                           final Function<? super T2, ? extends V> secondMap,
                           final Function<? super T3, ? extends V> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return secondMap.apply(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> firstMap,
                                                           final Function<? super T2, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> secondMap,
                                                           final Function<? super T3, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> thirdMap) {
            Objects.requireNonNull(firstMap, "The first flat map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second flat map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third flat map provided cannot be null");

            @SuppressWarnings("unchecked")
            final Either3<V1, V2, V3> res = (Either3<V1, V2, V3>) secondMap.apply(this.value);
            return Objects.requireNonNull(res, "The returned either cannot be null");
        }

        @Override
        public Either3<T2, T1, T3> swapFirst() {
            return Either3.first(this.value);
        }

        @Override
        public Either3<T1, T3, T2> swapSecond() {
            return Either3.third(this.value);
        }

        @Override
        public Stream<T1> streamFirst() {
            return Stream.empty();
        }

        @Override
        public Stream<T2> streamSecond() {
            return Stream.of(this.value);
        }

        @Override
        public Stream<T3> streamThird() {
            return Stream.empty();
        }

        @Override
        public Either3<T1, T2, T3> orFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return Either3.first(supplier.get());
        }

        @Override
        public Either3<T1, T2, T3> orSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return this;
        }

        @Override
        public Either3<T1, T2, T3> orThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return Either3.third(supplier.get());
        }

        @Override
        public T1 orElseFirst(final T1 other) {
            return other;
        }

        @Override
        public T1 orElseGetFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T1 orElseThrowFirst() {
            throw new NoSuchElementException("First value not present");
        }

        @Override
        public <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public T2 orElseSecond(final T2 other) {
            return this.value;
        }

        @Override
        public T2 orElseGetSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return this.value;
        }

        @Override
        public T2 orElseThrowSecond() {
            return this.value;
        }

        @Override
        public <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            return this.value;
        }

        @Override
        public T3 orElseThird(final T3 other) {
            return other;
        }

        @Override
        public T3 orElseGetThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T3 orElseThrowThird() {
            throw new NoSuchElementException("Third value not present");
        }

        @Override
        public <X extends Throwable> T3 orElseThrowThird(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            return o instanceof Either3.Second<?, ?, ?> second
                    && Objects.equals(this.value, second.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return "Second[" + this.value + "]";
        }
    }

    /**
     * A container object which can hold the third type.
     *
     * @param <T1> the first type of the value
     * @param <T2> the second type of the value
     * @param <T3> the third type of the value
     */
    protected static final class Third<T1, T2, T3> extends Either3<T1, T2, T3> {
        private final T3 value;

        /**
         * Default constructor.
         *
         * @param value the left value to describe, which must be non-{@code null}
         * @throws NullPointerException if {@code value} is {@code null}
         */
        private Third(final T3 value) {
            this.value = Objects.requireNonNull(value, "The third value supplied must not be null");
        }

        @Override
        public Optional<T1> first() {
            return Optional.empty();
        }

        @Override
        public Optional<T2> second() {
            return Optional.empty();
        }

        @Override
        public Optional<T3> third() {
            return Optional.of(this.value);
        }

        @Override
        public T1 firstRaw() {
            return null;
        }

        @Override
        public T2 secondRaw() {
            return null;
        }

        @Override
        public T3 thirdRaw() {
            return this.value;
        }

        @Override
        public boolean hasFirst() {
            return false;
        }

        @Override
        public boolean hasSecond() {
            return false;
        }

        @Override
        public boolean hasThird() {
            return true;
        }

        @Override
        public void ifPresent(final Consumer<? super T1> firstAction,
                              final Consumer<? super T2> secondAction,
                              final Consumer<? super T3> thirdAction) {
            Objects.requireNonNull(firstAction, "The first action provided cannot be null");
            Objects.requireNonNull(secondAction, "The second action provided cannot be null");
            Objects.requireNonNull(thirdAction, "The third action provided cannot be null");
            thirdAction.accept(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap,
                                                       final Function<? super T2, ? extends V2> secondMap,
                                                       final Function<? super T3, ? extends V3> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return Either3.third(thirdMap.apply(this.value));
        }

        @Override
        public <V> V mapTo(final Function<? super T1, ? extends V> firstMap,
                           final Function<? super T2, ? extends V> secondMap,
                           final Function<? super T3, ? extends V> thirdMap) {
            Objects.requireNonNull(firstMap, "The first map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third map provided cannot be null");
            return thirdMap.apply(this.value);
        }

        @Override
        public <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> firstMap,
                                                           final Function<? super T2, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> secondMap,
                                                           final Function<? super T3, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> thirdMap) {
            Objects.requireNonNull(firstMap, "The first flat map provided cannot be null");
            Objects.requireNonNull(secondMap, "The second flat map provided cannot be null");
            Objects.requireNonNull(thirdMap, "The third flat map provided cannot be null");

            @SuppressWarnings("unchecked")
            final Either3<V1, V2, V3> res = (Either3<V1, V2, V3>) thirdMap.apply(this.value);
            return Objects.requireNonNull(res, "The returned either cannot be null");
        }

        @Override
        public Either3<T2, T1, T3> swapFirst() {
            return Either3.third(this.value);
        }

        @Override
        public Either3<T1, T3, T2> swapSecond() {
            return Either3.second(this.value);
        }

        @Override
        public Stream<T1> streamFirst() {
            return Stream.empty();
        }

        @Override
        public Stream<T2> streamSecond() {
            return Stream.empty();
        }

        @Override
        public Stream<T3> streamThird() {
            return Stream.of(this.value);
        }

        @Override
        public Either3<T1, T2, T3> orFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return Either3.first(supplier.get());
        }

        @Override
        public Either3<T1, T2, T3> orSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return Either3.second(supplier.get());
        }

        @Override
        public Either3<T1, T2, T3> orThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return this;
        }

        @Override
        public T1 orElseFirst(final T1 other) {
            return other;
        }

        @Override
        public T1 orElseGetFirst(final Supplier<? extends T1> supplier) {
            Objects.requireNonNull(supplier, "The first supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T1 orElseThrowFirst() {
            throw new NoSuchElementException("First value not present");
        }

        @Override
        public <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public T2 orElseSecond(final T2 other) {
            return other;
        }

        @Override
        public T2 orElseGetSecond(final Supplier<? extends T2> supplier) {
            Objects.requireNonNull(supplier, "The second supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public T2 orElseThrowSecond() {
            throw new NoSuchElementException("Second value not present");
        }

        @Override
        public <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public T3 orElseThird(final T3 other) {
            return this.value;
        }

        @Override
        public T3 orElseGetThird(final Supplier<? extends T3> supplier) {
            Objects.requireNonNull(supplier, "The third supplier provided cannot be null");
            return this.value;
        }

        @Override
        public T3 orElseThrowThird() {
            return this.value;
        }

        @Override
        public <X extends Throwable> T3 orElseThrowThird(final Supplier<? extends X> exceptionSupplier) {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            return this.value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            return o instanceof Either3.Third<?, ?, ?> third
                    && Objects.equals(this.value, third.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return "Third[" + this.value + "]";
        }
    }
}
