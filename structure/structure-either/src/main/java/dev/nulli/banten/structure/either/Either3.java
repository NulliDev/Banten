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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Snapshot(SnapshotStage.IN_DEVELOPMENT)
public abstract class Either3<T1, T2, T3> {

    public static <T1, T2, T3> Either3<T1, T2, T3> first(final T1 value) {
        return null;
    }
    public static <T1, T2, T3> Either3<T1, T2, T3> second(final T2 value) {
        return null;
    }
    public static <T1, T2, T3> Either3<T1, T2, T3> third(final T3 value) {
        return null;
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

    public abstract void ifFirst(final Consumer<? super T1> action);
    public abstract void ifSecond(final Consumer<? super T2> action);
    public abstract void ifThird(final Consumer<? super T3> action);
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
    public abstract <V1, V2, V3> Either3<V1, V2, V3> mapAll(final Function<? super T1, ? extends V1> firstMap, final Function<? super T2, ? extends V2> secondMap, final Function<? super T3, ? extends V3> thirdMap);
    public abstract <V> V mapTo(final Function<? super T1, ? extends V> firstMap, final Function<? super T2, ? extends V> secondMap, final Function<? super T3, ? extends V> thirdMap);

    public <V> Either3<V, T2, T3> flatMapFirst(final Function<? super T1, ? extends Either3<V, T2, T3>> map) {
        return flatMapAll(map, Either3::second, Either3::third);
    }
    public <V> Either3<T1, V, T3> flatMapSecond(final Function<? super T2, ? extends Either3<T1, V, T3>> map) {
        return flatMapAll(Either3::first, map, Either3::third);
    }
    public <V> Either3<T1, T2, V> flatMapThird(final Function<? super T3, ? extends Either3<T1, T2, V>> map) {
        return flatMapAll(Either3::first, Either3::second, map);
    }
    public abstract <V1, V2, V3> Either3<V1, V2, V3> flatMapAll(final Function<? super T1, ? extends Either3<V1, V2, V3>> firstMap, final Function<? super T2, ? extends Either3<V1, V2, V3>> secondMap, final Function<? super T3, ? extends Either3<V1, V2, V3>> thirdMap);

    public abstract Either3<T2, T1, T3> swap1();
    public abstract Either3<T1, T3, T2> swap2();

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
}
