package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxLearning() {
        Flux<String> stringFlux = Flux.just("one", "two", "three")
                .concatWith(Flux.just("four"))
//                .concatWith(Flux.error(new RuntimeException("Error is here")))
                .concatWith(Flux.just("five"))
//                .log()
                ;

        stringFlux
                .subscribe(
                        (data) -> {
                            System.out.println(data);
                        },
                        (e) -> {
                            System.out.println(e.getMessage());
                        },
                        () -> {
                            System.out.println("Completed");
                        }
                );
    }

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("one", "two", "three")
                .concatWith(Flux.just("four"))
//                .concatWith(Flux.error(new RuntimeException("Error is here")))
                .concatWith(Flux.just("five"))
//                .log()
                ;

        StepVerifier.create(stringFlux)
                .expectNext("one")
                .expectNextCount(4)

//                .expectNextCount(3)
//                .expectError()
//                .verify();

                .verifyComplete();

    }

    @Test
    public void fluxTest2() {
        StepVerifier.create(Flux.just("one", "two", "three"))
                .expectNext("one")
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier.create(Flux.just("one", "two", "three").concatWith(Flux.error(new RuntimeException())))
                .expectNext("one")
                .expectNextCount(2)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoTest() {
        StepVerifier.create(Mono.just("one"))
                .expectNext("one")
                .verifyComplete();

        StepVerifier.create(Mono.error(new RuntimeException()))
                .expectError(RuntimeException.class)
                .verify();
    }
}
