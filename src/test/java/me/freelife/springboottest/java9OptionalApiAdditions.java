package me.freelife.springboottest;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class java9OptionalApiAdditions {

    /**
     * 2. The or() Method
     */
    @Test
    public void givenOptional_whenPresent_thenShouldTakeAValueFromIt() {
        //given
        String expected = "properValue";
        Optional<String> value = Optional.of(expected);
        Optional<String> defaultValue = Optional.of("default");

        //when
        Optional<String> result = value.or(() -> defaultValue);
        System.out.println(result);

        //then
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void givenOptional_whenEmpty_thenShouldTakeAValueFromOr() {
        //given
        String defaultString = "default";
        Optional<String> value = Optional.empty();
        Optional<String> defaultValue = Optional.of(defaultString);

        //when
        Optional<String> result = value.or(() -> defaultValue);

        //then
        assertThat(result.get()).isEqualTo(defaultString);
    }

    /**
     * 3. The ifPresentOrElse() Method
     */
    @Test
    public void givenOptional_whenPresent_thenShouldExecuteProperCallback() {
        //given
        Optional<String> value = Optional.of("properValue");
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        //when
        value.ifPresentOrElse(v -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet);

        //then
        assertThat(successCounter.get()).isEqualTo(1);
        assertThat(onEmptyOptionalCounter.get()).isEqualTo(0);
    }

    @Test
    public void givenOptional_whenNotPresent_thenShouldExecuteProperCallback() {
        //given
        Optional<String> value = Optional.empty();
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        //when
        value.ifPresentOrElse(v -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet);

        //then
        assertThat(successCounter.get()).isEqualTo(0);
        assertThat(onEmptyOptionalCounter.get()).isEqualTo(1);
    }

    /**
     * 4. The stream() Method
     */
    @Test
    public void givenOptionalOfSome_whenToStream_thenShouldTreatItAsOneElementStream() {
        //given
        Optional<String> value = Optional.of("a");

        //when
        List<String> collect = value.stream().map(String::toUpperCase).collect(Collectors.toList());

        //then
        assertThat(collect).hasSameElementsAs(List.of("A"));
    }

    @Test
    public void givenOptionalOfNone_whenToStream_thenShouldTreatItAsZeroElementStream() {
        //given
        Optional<String> value = Optional.empty();

        //when
        List<String> collect = value.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        //then
        assertThat(collect).isEmpty();
    }
}
