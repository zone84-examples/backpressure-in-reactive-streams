package tech.zone84.examples.backpressure;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import tech.zone84.examples.backpressure.pubsub.LimitedSubscriber;
import tech.zone84.examples.backpressure.pubsub.PublishingIterable;

import java.util.concurrent.CountDownLatch;

public class Example2 {
    public static void main(String args[]) throws InterruptedException {
        var latch = new CountDownLatch(1);
        var publisher = new PublishingIterable();
        var subscriber = new LimitedSubscriber(1, latch);

        Flux.fromIterable(publisher)
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .subscribe(subscriber);

        latch.await();
    }
}
