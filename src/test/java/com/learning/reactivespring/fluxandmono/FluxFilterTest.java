package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxFilterTest {

    @Test
    public void fluxFilter() {

        Flux<Integer> rangeFlux = Flux
                .range(1, 10)
                .filter(n -> n > 5);

        StepVerifier.create(rangeFlux)
                .expectNextCount(5)
                .verifyComplete();

    }

    @Test
    public void fluxFilterWithRepeat() {

        Flux<Integer> rangeFlux = Flux
                .range(1, 10)
                .repeat(1)
                .filter(n -> n > 5)
                .log();

        StepVerifier.create(rangeFlux)
                .expectNextCount(10)
                .verifyComplete();

    }
}
