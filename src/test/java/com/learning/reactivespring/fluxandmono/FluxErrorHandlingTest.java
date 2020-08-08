package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxErrorHandlingTest {

    @Test
    public void usingOnErrorResume() {
        Flux<Integer> integerFlux = Flux.range(1, 3)
                .concatWith(Flux.error(new RuntimeException("Error - ")))
                .onErrorResume(e -> {
                    System.out.println(e.getMessage());
                    return Flux.just(0);
                });

        StepVerifier.create(integerFlux)
                .expectNextCount(3)
                .expectNext(0)
                .verifyComplete();

    }

    @Test
    public void usingOnErrorReturn() {
        Flux<Integer> integerFlux = Flux.range(1, 3)
                .concatWith(Flux.error(new RuntimeException("Error - ")))
                .onErrorReturn(0);

        StepVerifier.create(integerFlux)
                .expectNextCount(3)
                .expectNext(0)
                .verifyComplete();

    }

    @Test
    public void usingOnErrorMap() {
        Flux<Integer> integerFlux = Flux.range(1, 3)
                .concatWith(Flux.error(new RuntimeException("Error - ")))
                .onErrorMap(e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()));

        StepVerifier.create(integerFlux)
                .expectNextCount(3)
                .expectError(ResponseStatusException.class)
                .verify();

    }

    @Test
    public void usingRetry() {
        Flux<Integer> integerFlux = Flux.range(1, 3)
                .concatWith(Flux.error(new RuntimeException("Error - ")))
                .retry(1);

        StepVerifier.create(integerFlux)
                .expectNextCount(6) // 1, 2, 3, 1, 2, 3
                .expectError(RuntimeException.class)
                .verify();

    }
}
