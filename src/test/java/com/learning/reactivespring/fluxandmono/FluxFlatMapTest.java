package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxFlatMapTest {

    public Integer makeDouble(Integer d) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return d * 2;
    }

    @Test
    public void flatMapTest() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .flatMap((s) -> {
                    return Flux.just(makeDouble(s));
                })
                .log();

        integerFlux.subscribe(System.out::println);
    }


    @Test
    public void flatMapParallelTest() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .window(3)
                .flatMap((s) -> s.map(this::makeDouble).subscribeOn(Schedulers.parallel()))
//                .flatMapSequential((s) -> s.map(this::makeDouble).subscribeOn(Schedulers.parallel()))
                .log();

        StepVerifier.create(integerFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
