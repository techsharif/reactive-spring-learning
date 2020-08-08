package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTransformTest {

    @Test
    public void fluxTransform() {

        Flux<Integer> integerFlux = Flux.range(5, 5)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .log();

        StepVerifier.create(integerFlux)
                .expectNext(12, 16)
                .verifyComplete();

    }
}
