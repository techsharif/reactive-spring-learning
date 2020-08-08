package com.learning.reactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxCombineTest {

    @Test
    public void usingMerge() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.merge(f1, f2);

        StepVerifier.create(f1f2.log())
                .expectNextCount(6) // 1, 2, 3, 4, 5, 6 sequence not guarantied
                .verifyComplete();
    }

    @Test
    public void usingMergeWithDelay() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3).delayElements(Duration.ofSeconds(1)); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3).delayElements(Duration.ofSeconds(1)); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.merge(f1, f2);

        StepVerifier.create(f1f2.log())
                .expectNextCount(6) // sequence not guarantied
                .verifyComplete();
    }

    @Test
    public void usingConcat() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.concat(f1, f2);

        StepVerifier.create(f1f2.log())
                .expectNext(1, 2, 3, 4, 5, 6) // 1, 2, 3, 4, 5, 6 sequence guarantied
                .verifyComplete();
    }

    @Test
    public void usingConcatWithDelay() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3).delayElements(Duration.ofSeconds(1)); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3).delayElements(Duration.ofSeconds(1)); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.concat(f1, f2);

        StepVerifier.create(f1f2.log())
                .expectNext(1, 2, 3, 4, 5, 6) // 1, 2, 3, 4, 5, 6 sequence guarantied
                .verifyComplete();
    }

    @Test
    public void usingZip() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.zip(f1, f2, (a, b) -> a + b);

        StepVerifier.create(f1f2.log())
                .expectNext(5, 7, 9)
                .verifyComplete();
    }

    @Test
    public void usingZipWithDelay() { // not sequential
        Flux<Integer> f1 = Flux.range(1, 3).delayElements(Duration.ofSeconds(1)); // 1, 2, 3
        Flux<Integer> f2 = Flux.range(4, 3).delayElements(Duration.ofSeconds(2)); // 4, 5, 6

        Flux<Integer> f1f2 = Flux.zip(f1, f2, (a, b) -> a + b);

        StepVerifier.create(f1f2.log())
                .expectNext(5, 7, 9)
                .verifyComplete();
    }

}
