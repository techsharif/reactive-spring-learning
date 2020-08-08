package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxControlRequestAndCancelTest {

    @Test
    public void directRequest() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        integerFlux.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Done"),
                subscription -> subscription.request(3)
        );
    }

    @Test
    public void directCancel() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        integerFlux.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Done"),
                Subscription::cancel
        );
    }

    @Test
    public void directMultipleRequestWithCancle() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        integerFlux.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Done"),
                subscription -> {
                    subscription.request(3);
                    subscription.cancel();
                }
        );
    }

    @Test
    public void customRequestAndCancel() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();

        integerFlux.subscribe(
                new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println(value);
                        if (value == 4) cancel();
                        else request(1);
                    }
                }
        );
    }


}
