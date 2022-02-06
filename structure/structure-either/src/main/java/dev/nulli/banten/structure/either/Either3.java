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

import dev.nulli.banten.annotation.core.Snapshot;
import dev.nulli.banten.annotation.core.SnapshotStage;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Snapshot(SnapshotStage.IN_DEVELOPMENT)
public abstract sealed class Either3<T1, T2, T3> permits Either3.First, Either3.Second, Either3.Third {

    public static <T1, T2, T3> Either3<T1, T2, T3> first(final T1 value) {
        return new First<>(value);
    }
    public static <T1, T2, T3> Either3<T1, T2, T3> second(final T2 value) {
        return new Second<>(value);
    }
    public static <T1, T2, T3> Either3<T1, T2, T3> third(final T3 value) {
        return new Third<>(value);
    }

    public abstract Optional<T1> first();
    public abstract Optional<T2> second();
    public abstract Optional<T3> third();

    public abstract T1 firstRaw();
    public abstract T2 secondRaw();
    public abstract T3 thirdRaw();

    public abstract boolean hasFirst();
    public abstract boolean hasSecond();
    public abstract boolean hasThird();

    public void ifFirst(final Consumer<? super T1> action) {
        this.ifPresent(action, $ -> {}, $ -> {});
    }
    public void ifSecond(final Consumer<? super T2> action)  {
        this.ifPresent($ -> {}, action, $ -> {});
    }
    public void ifThird(final Consumer<? super T3> action)  {
        this.ifPresent($ -> {}, $ -> {}, action);
    }
    public abstract void ifPresent(final Consumer<? super T1> firstAction, final Consumer<? super T2> secondAction, final Consumer<? super T3> thirdAction);

    public <V> Either3<V, T2, T3> mapFirst(final Function<? super T1, ? extends V> map) {
        return mapAll(map, Function.identity(), Function.identity());
    }
    public <V> Either3<T1, V, T3> mapSecond(final Function<? super T2, ? extends V> map) {
        return mapAll(Function.identity(), map, Function.identity());
    }
    public <V> Either3<T1, T2, V> mapThird(final Function<? super T3, ? extends V> map) {
        return mapAll(Function.identity(), Function.identity(), map);
    }
    public abstract <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap,
                                                            final Function<? super T2, ? extends V2> secondMap,
                                                            final Function<? super T3, ? extends V3> thirdMap);
    public abstract <V> V mapTo(final Function<? super T1, ? extends V> firstMap,
                                final Function<? super T2, ? extends V> secondMap,
                                final Function<? super T3, ? extends V> thirdMap);

    public <V> Either3<V, T2, T3> flatMapFirst(final Function<? super T1, ? extends Either3<? extends V, ? extends T2, ? extends T3>> map) {
        return flatMapAll(map, Either3::second, Either3::third);
    }
    public <V> Either3<T1, V, T3> flatMapSecond(final Function<? super T2, ? extends Either3<? extends T1, ? extends V, ? extends T3>> map) {
        return flatMapAll(Either3::first, map, Either3::third);
    }
    public <V> Either3<T1, T2, V> flatMapThird(final Function<? super T3, ? extends Either3<? extends T1, ? extends T2, ? extends V>> map) {
        return flatMapAll(Either3::first, Either3::second, map);
    }
    public abstract <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> firstMap,
                                                                final Function<? super T2, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> secondMap,
                                                                final Function<? super T3, ? extends Either3<? extends V1, ? extends V2, ? extends V3>> thirdMap);

    public abstract Either3<T2, T1, T3> swapFirst();
    public abstract Either3<T1, T3, T2> swapSecond();

    public abstract Stream<T1> streamFirst();
    public abstract Stream<T2> streamSecond();
    public abstract Stream<T3> streamThird();

    public abstract Either3<T1, T2, T3> orFirst(final Supplier<? extends T1> supplier);
    public abstract Either3<T1, T2, T3> orSecond(final Supplier<? extends T2> supplier);
    public abstract Either3<T1, T2, T3> orThird(final Supplier<? extends T3> supplier);

    public abstract T1 orElseFirst(final T1 other);
    public abstract T1 orElseGetFirst(final Supplier<? extends T1> supplier);
    public abstract T1 orElseThrowFirst();
    public abstract <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) throws X;
    public abstract T2 orElseSecond(final T2 other);
    public abstract T2 orElseGetSecond(final Supplier<? extends T2> supplier);
    public abstract T2 orElseThrowSecond();
    public abstract <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) throws X;
    public abstract T3 orElseThird(final T3 other);
    public abstract T3 orElseGetThird(final Supplier<? extends T3> supplier);
    public abstract T3 orElseThrowThird();
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
        public <X extends Throwable> T1 orElseThrowFirst(final Supplier<? extends X> exceptionSupplier) throws X {
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
        public <X extends Throwable> T2 orElseThrowSecond(final Supplier<? extends X> exceptionSupplier) throws X {
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
        public <X extends Throwable> T3 orElseThrowThird(final Supplier<? extends X> exceptionSupplier) throws X {
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
