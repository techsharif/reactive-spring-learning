package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FluxAndMonoFactoryMethodTest {

    @Test
    public void fluxFactoryMethods() {
        String[] strings = {"one", "two", "three"};
        List<String> stringList = Arrays.asList(strings);
        Stream<String> streamList = stringList.stream();

        Flux<String> arrFlux = Flux.fromArray(strings);
        Flux<String> listFlux = Flux.fromIterable(stringList);
        Flux<String> streamFlux = Flux.fromStream(streamList);

        fluxStepVerify(arrFlux);
        fluxStepVerify(listFlux);
        fluxStepVerify(streamFlux);

        // using range
        Flux<Integer> integerFlux = Flux.range(1, 3);
        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNextCount(2)
                .verifyComplete();

    }

    private void fluxStepVerify(Flux<String> stringFlux) {
        StepVerifier.create(stringFlux)
                .expectNext("one")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void monoFactoryMethods() {

        // from supplier
        Supplier<String> stringSupplier = () -> "one";
        Mono<String> supplierMono = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(supplierMono)
                .expectNext("one")
                .verifyComplete();

        // from just
        StepVerifier.create(Mono.just("one"))
                .expectNext("one")
                .verifyComplete();

        StepVerifier.create(Mono.empty())
                .verifyComplete();

        StepVerifier.create(Mono.justOrEmpty("one"))
                .expectNext("one")
                .verifyComplete();

        StepVerifier.create(Mono.justOrEmpty(null))
                .verifyComplete();

    }
}
