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
 * A container object which can hold one of the two types defined. If one value
 * type is present, then the other value type will not be. If the left value
 * type is present, {@link #hasLeft()} returns {@code true}. If the right value
 * is present, {@link #hasRight()} returns {@code true}.
 *
 * <p>Additional methods that depend on the presence or absence left or right
 * value are provided, such as {@link #orElseLeft(Object)} or {@link #orElseRight(Object)},
 * which return a default value if the associated value type is not present, and
 * {@link #ifLeft(Consumer)} or {@link #ifRight(Consumer)}, which performs an
 * action if the associated value type is present.
 *
 * @apiNote
 * {@code Either} is primarily intended for use when a value can be represented
 * or used as multiple types. A value represented should never itself be
 * {@code null}.
 *
 * @param <L> the type of the left value
 * @param <R> the type of the right value
 *
 * @since 1.0.0
 * @author Aaron Haim
 */
public abstract sealed class Either<L, R> permits Either.Left, Either.Right {

    /**
     * Returns an {@code Either} with the non-{@code null} left value present.
     *
     * @param value the left value, which must be non-{@code null}
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     * @return an {@code Either} with the left value present
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <L, R> Either<L, R> left(final L value) {
        return new Left<>(value);
    }

    /**
     * Returns an {@code Either} with the non-{@code null} right value present.
     *
     * @param value the right value, which must be non-{@code null}
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     * @return an {@code Either} with the right value present
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <L, R> Either<L, R> right(final R value) {
        return new Right<>(value);
    }

    /**
     * If the left value is present, returns an {@link Optional} describing the
     * left value, otherwise an empty {@link Optional}.
     *
     * @return an {@link Optional} describing the left value if present, otherwise
     *         an empty {@link Optional}
     */
    public abstract Optional<L> left();

    /**
     * If the right value is present, returns an {@link Optional} describing the
     * right value, otherwise an empty {@link Optional}.
     *
     * @return an {@link Optional} describing the right value if present, otherwise
     *         an empty {@link Optional}
     */
    public abstract Optional<R> right();

    /**
     * If the left value is present, returns the value, otherwise {@code null}.
     *
     * @return the left value if present, otherwise {@code null}
     */
    public abstract L leftRaw();

    /**
     * If the right value is present, returns the value, otherwise {@code null}.
     *
     * @return the right value if present, otherwise {@code null}
     */
    public abstract R rightRaw();

    /**
     * If the left value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if the left value is present, otherwise {@code false}
     */
    public abstract boolean hasLeft();

    /**
     * If the right value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if the right value is present, otherwise {@code false}
     */
    public abstract boolean hasRight();

    /**
     * If the left value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if the left value is present
     * @throws NullPointerException if the given action is {@code null}
     */
    public abstract void ifLeft(final Consumer<? super L> action);

    /**
     * If the right value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if the right value is present
     * @throws NullPointerException if the given action is {@code null}
     */
    public abstract void ifRight(final Consumer<? super R> action);

    /**
     * Performs one of the given actions depending on which value is present.
     *
     * @param leftAction the action to be performed, if the left value is present
     * @param rightAction the action to be performed, if the right value is present
     * @throws NullPointerException if either action is {@code null}
     */
    public abstract void ifPresent(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction);

    /**
     * If the left value is present, returns an {@code Either} describing the
     * result of applying the given mapping function to the left value, otherwise
     * returns an {@code Either} with the same right value.
     *
     * @param map the mapping function to apply to the left value, if present
     * @param <V> the type of the left value returned from the mapping function
     * @return an {@link Either} describing the result of applying a mapping
     *         function to the left value, if present, otherwise an {@link Either}
     *         with the same right value
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either<V, R> mapLeft(final Function<? super L, ? extends V> map) {
        return mapAll(map, Function.identity());
    }

    /**
     * If the right value is present, returns an {@code Either} describing the
     * result of applying the given mapping function to the right value, otherwise
     * returns an {@code Either} with the same left value.
     *
     * @param map the mapping function to apply to the right value, if present
     * @param <V> the type of the right value returned from the mapping function
     * @return an {@link Either} describing the result of applying a mapping
     *         function to the right value, if present, otherwise an {@link Either}
     *         with the same left value
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either<L, V> mapRight(final Function<? super R, ? extends V> map) {
        return mapAll(Function.identity(), map);
    }

    /**
     * Returns an {@link Either} describing the result of applying the given
     * mapping function to the value that is present.
     *
     * @param leftMap the mapping function to apply to the left value, if present
     * @param rightMap the mapping function to apply to the right value, if present
     * @param <VL> the type of the left value returned from the mapping function
     * @param <VR> the type of the right value returned from the mapping function
     * @return an {@link Either} describing the result of applying a mapping function
     *         to the present value
     * @throws NullPointerException if either mapping functions or the results of
     *                              the mapping functions are {@code null}
     */
    public abstract <VL, VR> Either<VL, VR> mapAll(final Function<? super L, ? extends VL> leftMap, final Function<? super R, ? extends VR> rightMap);

    /**
     * Returns a value describing the result of applying the given mapping function
     * to the value that is present.
     *
     * @param leftMap the mapping function to apply to the left value, if present
     * @param rightMap the mapping function to apply to the right value, if present
     * @param <V> the type of the value returned from the mapping function
     * @return a value from the result of applying a mapping function to the present
     *         value
     * @throws NullPointerException if either mapping functions are {@code null}
     */
    public abstract <V> V mapTo(final Function<? super L, ? extends V> leftMap, final Function<? super R, ? extends V> rightMap);

    /**
     * If the left value is present, returns the result of applying the given
     * {@code Either}-bearing mapping function to the left value, otherwise
     * returns an {@code Either} with the same right value.
     *
     * @param map the mapping function to apply to the left value, if present
     * @param <V> the type of the left value of the {@code Either} returned by
     *            the mapping function
     * @return the result of apply an {@code Either}-bearing mapping function to
     *         the left value of this {@code Either}, if present, otherwise an
     *         {@code Either} with the same right value
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either<V, R> flatMapLeft(final Function<? super L, ? extends Either<V, R>> map) {
        return flatMapAll(map, Either::right);
    }

    /**
     * If the right value is present, returns the result of applying the given
     * {@code Either}-bearing mapping function to the right value, otherwise
     * returns an {@code Either} with the same left value.
     *
     * @param map the mapping function to apply to the right value, if present
     * @param <V> the type of the right value of the {@code Either} returned by
     *            the mapping function
     * @return the result of apply an {@code Either}-bearing mapping function to
     *         the right value of this {@code Either}, if present, otherwise an
     *         {@code Either} with the same left value
     * @throws NullPointerException if the mapping function or the result of the
     *                              mapping function is {@code null}
     */
    public <V> Either<L, V> flatMapRight(final Function<? super R, ? extends Either<L, V>> map) {
        return flatMapAll(Either::left, map);
    }

    /**
     * Returns the result of applying the given {@code Either}-bearing mapping
     * function to the value that is present.
     *
     * @param leftMap the mapping function to apply to the left value, if present
     * @param rightMap the mapping function to apply to the right value, if present
     * @param <VL> the type of the left value of the {@code Either} returned by the
     *             mapping function
     * @param <VR> the type of the right value of the {@code Either} returned by the
     *             mapping function
     * @return the result of applying an {@code Either}-bearing mapping function to
     *         the present value
     * @throws NullPointerException if either mapping functions or the results of
     *                              the mapping function are {@code null}
     *
     */
    public abstract <VL, VR> Either<VL, VR> flatMapAll(final Function<? super L, ? extends Either<VL, VR>> leftMap, final Function<? super R, ? extends Either<VL, VR>> rightMap);

    /**
     * Swaps the types of the {@code Either} such that the left value becomes the
     * right value or vice versa if present.
     *
     * @return an {@code Either} where the left value becomes the right value or
     *         vice versa, if present
     */
    public abstract Either<R, L> swap();

    /**
     * If the left value is present, a {@link Stream} is returned containing
     * only the left value, otherwise an empty {@link Stream} is returned.
     *
     * @return the left {@code Either} value as a {@link Stream}
     */
    public abstract Stream<L> streamLeft();

    /**
     * If the right value is present, a {@link Stream} is returned containing
     * only the right value, otherwise an empty {@link Stream} is returned.
     *
     * @return the right {@code Either} value as a {@link Stream}
     */
    public abstract Stream<R> streamRight();

    /**
     * If the left value is present, returns this {@code Either}, otherwise
     * returns an {@code Either} with the supplied left value.
     *
     * @param supplier the supplying function that produces the left value
     * @return this {@code Either}, if the left value is present, otherwise an
     *         {@code Either} with the supplied left value
     * @throws NullPointerException if the supplier or the left value of the
     *                              supplier is {@code null}
     */
    public abstract Either<L, R> orLeft(final Supplier<? extends L> supplier);

    /**
     * If the right value is present, returns this {@code Either}, otherwise
     * returns an {@code Either} with the supplied right value.
     *
     * @param supplier the supplying function that produces the right value
     * @return this {@code Either}, if the right value is present, otherwise an
     *         {@code Either} with the supplied right value
     * @throws NullPointerException if the supplier or the right value of the
     *                              supplier is {@code null}
     */
    public abstract Either<L, R> orRight(final Supplier<? extends R> supplier);

    /**
     * If the left value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no left value is present
     * @return the left value, if present, otherwise {@code other}
     */
    public abstract L orElseLeft(final L other);

    /**
     * If the left value is present, returns the value, otherwise returns the
     * result produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the left value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if the supplying function is {@code null}
     */
    public abstract L orElseGetLeft(final Supplier<? extends L> supplier);

    /**
     * If the left value is present, returns the value, otherwise throws
     * {@link NoSuchElementException}.
     *
     * @return the non-{@code null} left value described by this {@code Either}
     * @throws NoSuchElementException if no left value is present
     */
    public abstract L orElseThrowLeft();

    /**
     * If the left value is present, returns the value, otherwise throws an
     * exception produced by the exception supplying function.
     *
     * @param exceptionSupplier the supplying function that produces an exception
     *                          to be thrown
     * @param <X> the type of the exception to be thrown
     * @return the left value, if present
     * @throws X if no left value is present
     * @throws NullPointerException if the supplying function or the exception
     *                              from the supplier is {@code null}
     */
    public abstract <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * If the right value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no right value is present
     * @return the right value, if present, otherwise {@code other}
     */
    public abstract R orElseRight(final R other);

    /**
     * If the right value is present, returns the value, otherwise returns the
     * result produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the right value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if the supplying function is {@code null}
     */
    public abstract R orElseGetRight(final Supplier<? extends R> supplier);

    /**
     * If the right value is present, returns the value, otherwise throws
     * {@link NoSuchElementException}.
     *
     * @return the non-{@code null} right value described by this {@code Either}
     * @throws NoSuchElementException if no right value is present
     */
    public abstract R orElseThrowRight();

    /**
     * If the right value is present, returns the value, otherwise throws an
     * exception produced by the exception supplying function.
     *
     * @param exceptionSupplier the supplying function that produces an exception
     *                          to be thrown
     * @param <X> the type of the exception to be thrown
     * @return the right value, if present
     * @throws X if no left value is present
     * @throws NullPointerException if the supplying function or the exception
     *                              from the supplier is {@code null}
     */
    public abstract <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * A container object which can hold the left type.
     *
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     */
    protected static final class Left<L, R> extends Either<L, R> {
        private final L value;

        /**
         * Default constructor.
         *
         * @param value the left value to describe, which must be non-{@code null}
         * @throws NullPointerException if {@code value} is {@code null}
         */
        private Left(final L value) {
            this.value = Objects.requireNonNull(value, "The value supplied on the left must not be null");
        }

        @Override
        public Optional<L> left() {
            return Optional.of(this.value);
        }

        @Override
        public Optional<R> right() {
            return Optional.empty();
        }

        @Override
        public L leftRaw() {
            return this.value;
        }

        @Override
        public R rightRaw() {
            return null;
        }

        @Override
        public boolean hasLeft() {
            return true;
        }

        @Override
        public boolean hasRight() {
            return false;
        }

        @Override
        public void ifLeft(final Consumer<? super L> action) {
            Objects.requireNonNull(action, "The left action provided cannot be null");
            action.accept(this.value);
        }

        @Override
        public void ifRight(final Consumer<? super R> action) {
            Objects.requireNonNull(action, "The right action provided cannot be null");
        }

        @Override
        public void ifPresent(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction) {
            Objects.requireNonNull(leftAction, "The left action provided cannot be null");
            Objects.requireNonNull(rightAction, "The right action provided cannot be null");
            this.ifLeft(leftAction);
        }

        @Override
        public <VL, VR> Either<VL, VR> mapAll(final Function<? super L, ? extends VL> leftMap, final Function<? super R, ? extends VR> rightMap) {
            Objects.requireNonNull(leftMap, "The left map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right map provided cannot be null");
            return Either.left(leftMap.apply(this.value));
        }

        @Override
        public <V> V mapTo(final Function<? super L, ? extends V> leftMap, final Function<? super R, ? extends V> rightMap) {
            Objects.requireNonNull(leftMap, "The left map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right map provided cannot be null");
            return leftMap.apply(this.value);
        }

        @Override
        public <VL, VR> Either<VL, VR> flatMapAll(Function<? super L, ? extends Either<VL, VR>> leftMap, Function<? super R, ? extends Either<VL, VR>> rightMap) {
            Objects.requireNonNull(leftMap, "The left flat map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right flat map provided cannot be null");
            return Objects.requireNonNull(leftMap.apply(this.value), "The returned either cannot be null");
        }

        @Override
        public Either<R, L> swap() {
            return Either.right(this.value);
        }

        @Override
        public Stream<L> streamLeft() {
            return Stream.of(this.value);
        }

        @Override
        public Stream<R> streamRight() {
            return Stream.empty();
        }

        @Override
        public Either<L, R> orLeft(final Supplier<? extends L> supplier) {
            Objects.requireNonNull(supplier, "The left supplier provided cannot be null");
            return this;
        }

        @Override
        public Either<L, R> orRight(final Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "The right supplier provided cannot be null");
            return Either.right(supplier.get());
        }

        @Override
        public L orElseLeft(final L other) {
            return this.value;
        }

        @Override
        public L orElseGetLeft(final Supplier<? extends L> supplier) {
            Objects.requireNonNull(supplier, "The left supplier provided cannot be null");
            return this.value;
        }

        @Override
        public L orElseThrowLeft() {
            return this.value;
        }

        @Override
        public <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            return this.value;
        }

        @Override
        public R orElseRight(final R other) {
            return other;
        }

        @Override
        public R orElseGetRight(final Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "The right supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public R orElseThrowRight() {
            throw new NoSuchElementException("Right value not present");
        }

        @Override
        public <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o instanceof Left<? , ?> left
                    && Objects.equals(this.value, left.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return "Left[" + this.value + "]";
        }
    }

    /**
     * A container object which can hold the right type.
     *
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     */
    protected static final class Right<L, R> extends Either<L, R> {
        private final R value;

        /**
         * Default constructor.
         *
         * @param value the right value to describe, which must be non-{@code null}
         * @throws NullPointerException if {@code value} is {@code null}
         */
        private Right(final R value) {
            this.value = Objects.requireNonNull(value, "The value supplied on the right must not be null");
        }

        @Override
        public Optional<L> left() {
            return Optional.empty();
        }

        @Override
        public Optional<R> right() {
            return Optional.of(this.value);
        }

        @Override
        public L leftRaw() {
            return null;
        }

        @Override
        public R rightRaw() {
            return this.value;
        }

        @Override
        public boolean hasLeft() {
            return false;
        }

        @Override
        public boolean hasRight() {
            return true;
        }

        @Override
        public void ifLeft(final Consumer<? super L> action) {
            Objects.requireNonNull(action, "The left action provided cannot be null");
        }

        @Override
        public void ifRight(final Consumer<? super R> action) {
            Objects.requireNonNull(action, "The right action provided cannot be null");
            action.accept(this.value);
        }

        @Override
        public void ifPresent(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction) {
            Objects.requireNonNull(leftAction, "The left action provided cannot be null");
            Objects.requireNonNull(rightAction, "The right action provided cannot be null");
            this.ifRight(rightAction);
        }

        @Override
        public <VL, VR> Either<VL, VR> mapAll(final Function<? super L, ? extends VL> leftMap, final Function<? super R, ? extends VR> rightMap) {
            Objects.requireNonNull(leftMap, "The left map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right map provided cannot be null");
            return Either.right(rightMap.apply(this.value));
        }

        @Override
        public <V> V mapTo(final Function<? super L, ? extends V> leftMap, final Function<? super R, ? extends V> rightMap) {
            Objects.requireNonNull(leftMap, "The left map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right map provided cannot be null");
            return rightMap.apply(this.value);
        }

        @Override
        public <VL, VR> Either<VL, VR> flatMapAll(Function<? super L, ? extends Either<VL, VR>> leftMap, Function<? super R, ? extends Either<VL, VR>> rightMap) {
            Objects.requireNonNull(leftMap, "The left flat map provided cannot be null");
            Objects.requireNonNull(rightMap, "The right flat map provided cannot be null");
            return Objects.requireNonNull(rightMap.apply(this.value), "The returned either cannot be null");
        }

        @Override
        public Either<R, L> swap() {
            return Either.left(this.value);
        }

        @Override
        public Stream<L> streamLeft() {
            return Stream.empty();
        }

        @Override
        public Stream<R> streamRight() {
            return Stream.of(this.value);
        }

        @Override
        public Either<L, R> orLeft(final Supplier<? extends L> supplier) {
            Objects.requireNonNull(supplier, "The left supplier provided cannot be null");
            return Either.left(supplier.get());
        }

        @Override
        public Either<L, R> orRight(final Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "The right supplier provided cannot be null");
            return this;
        }

        @Override
        public L orElseLeft(final L other) {
            return other;
        }

        @Override
        public L orElseGetLeft(final Supplier<? extends L> supplier) {
            Objects.requireNonNull(supplier, "The left supplier provided cannot be null");
            return supplier.get();
        }

        @Override
        public L orElseThrowLeft() {
            throw new NoSuchElementException("Left value not present");
        }

        @Override
        public <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) throws X {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            throw Objects.requireNonNull(exceptionSupplier.get(), "The thrown exception provided cannot be null");
        }

        @Override
        public R orElseRight(final R other) {
            return this.value;
        }

        @Override
        public R orElseGetRight(final Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "The right supplier provided cannot be null");
            return this.value;
        }

        @Override
        public R orElseThrowRight() {
            return this.value;
        }

        @Override
        public <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) {
            Objects.requireNonNull(exceptionSupplier, "The exception supplier provided cannot be null");
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o instanceof Right<? , ?> right
                    && Objects.equals(this.value, right.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return "Right[" + this.value + "]";
        }
    }
}
