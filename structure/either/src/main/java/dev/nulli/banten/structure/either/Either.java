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

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

//TODO: Finish, Document
@Snapshot
public abstract sealed class Either<L, R> permits Either.Left, Either.Right {

    public static <L, R> Either<L, R> left(final L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(final R value) {
        return new Right<>(value);
    }

    // Get wrapped values
    public abstract Optional<L> left();
    public abstract Optional<R> right();

    // Get raw values, Can be null
    public abstract L leftRaw();
    public abstract R rightRaw();

    // Check if values exist
    public abstract boolean hasLeft();
    public abstract boolean hasRight();

    // Execute when exists
    public abstract void ifLeft(final Consumer<? super L> action);
    public abstract void ifRight(final Consumer<? super R> action);
    public abstract void ifPresent(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction);

    // Map values to other values
    public abstract <V> Either<V, R> mapLeft(final Function<? super L, ? extends V> map);
    public abstract <V> Either<L, V> mapRight(final Function<? super R, ? extends V> map);
    public abstract <VL, VR> Either<VL, VR> mapAll(final Function<? super L, ? extends VL> leftMap, final Function<? super R, ? extends VR> rightMap);
    public abstract <V> V mapTo(final Function<? super L, ? extends V> leftMap, final Function<? super R, ? extends V> rightMap);

    // Flat Map values to other values
    public abstract <V> Either<V, R> flatMapLeft(final Function<? super L, ? extends Either<V, R>> map);
    public abstract <V> Either<L, V> flatMapRight(final Function<? super R, ? extends Either<L, V>> map);
    public abstract <VL, VR> Either<VL, VR> flatMapAll(final Function<? super L, ? extends Either<VL, VR>> leftMap, final Function<? super R, ? extends Either<VL, VR>> rightMap);

    // Swap left and right values
    public abstract Either<R, L> swap();

    // Stream left and right values
    public abstract Stream<L> streamLeft();
    public abstract Stream<R> streamRight();

    // Set value if not present
    public abstract Either<L, R> orLeft(final Supplier<? extends L> supplier);
    public abstract Either<L, R> orRight(final Supplier<? extends R> supplier);

    // Get alternative if not present
    public abstract L orElseLeft(final L other);
    public abstract L orElseGetLeft(final Supplier<? extends L> supplier);
    public abstract L orElseThrowLeft();
    public abstract <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) throws X;
    public abstract R orElseRight(final R other);
    public abstract R orElseGetRight(final Supplier<? extends R> supplier);
    public abstract R orElseThrowRight();
    public abstract <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) throws X;

    protected static final class Left<L, R> extends Either<L, R> {
        private final L value;

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
        public <V> Either<V, R> mapLeft(final Function<? super L, ? extends V> map) {
            Objects.requireNonNull(map, "The left map provided cannot be null");
            return Either.left(map.apply(this.value));
        }

        @Override
        public <V> Either<L, V> mapRight(final Function<? super R, ? extends V> map) {
            Objects.requireNonNull(map, "The right map provided cannot be null");
            return Either.left(this.value);
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
        public <V> Either<V, R> flatMapLeft(final Function<? super L, ? extends Either<V, R>> map) {
            Objects.requireNonNull(map, "The left flat map provided cannot be null");
            return Objects.requireNonNull(map.apply(this.value), "The returned either cannot be null");
        }

        @Override
        public <V> Either<L, V> flatMapRight(Function<? super R, ? extends Either<L, V>> map) {
            Objects.requireNonNull(map, "The right flat map provided cannot be null");
            return Either.left(this.value);
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
            return this.value;
        }

        @Override
        public L orElseThrowLeft() {
            return this.value;
        }

        @Override
        public <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }

        @Override
        public R orElseRight(final R other) {
            return other;
        }

        @Override
        public R orElseGetRight(final Supplier<? extends R> supplier) {
            return supplier.get();
        }

        @Override
        public R orElseThrowRight() {
            throw new NoSuchElementException("Right value not present");
        }

        @Override
        public <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
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

    protected static final class Right<L, R> extends Either<L, R> {
        private final R value;

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
        public <V> Either<V, R> mapLeft(final Function<? super L, ? extends V> map) {
            Objects.requireNonNull(map, "The left map provided cannot be null");
            return Either.right(this.value);
        }

        @Override
        public <V> Either<L, V> mapRight(final Function<? super R, ? extends V> map) {
            Objects.requireNonNull(map, "The right map provided cannot be null");
            return Either.right(map.apply(this.value));
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
        public <V> Either<V, R> flatMapLeft(final Function<? super L, ? extends Either<V, R>> map) {
            Objects.requireNonNull(map, "The left flat map provided cannot be null");
            return Either.right(this.value);
        }

        @Override
        public <V> Either<L, V> flatMapRight(Function<? super R, ? extends Either<L, V>> map) {
            Objects.requireNonNull(map, "The right flat map provided cannot be null");
            return Objects.requireNonNull(map.apply(this.value), "The returned either cannot be null");
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
            return supplier.get();
        }

        @Override
        public L orElseThrowLeft() {
            throw new NoSuchElementException("Left value not present");
        }

        @Override
        public <X extends Throwable> L orElseThrowLeft(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        @Override
        public R orElseRight(final R other) {
            return this.value;
        }

        @Override
        public R orElseGetRight(final Supplier<? extends R> supplier) {
            return this.value;
        }

        @Override
        public R orElseThrowRight() {
            return this.value;
        }

        @Override
        public <X extends Throwable> R orElseThrowRight(final Supplier<? extends X> exceptionSupplier) throws X {
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
