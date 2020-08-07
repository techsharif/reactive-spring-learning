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
}
