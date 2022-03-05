package tech.zone84.examples.backpressure;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import tech.zone84.examples.backpressure.pubsub.OneByOneSubscriber;
import tech.zone84.examples.backpressure.pubsub.PublishingIterable;

import java.util.concurrent.CountDownLatch;

public class Example1 {
    private static final int PREFETCH_SIZE = 1;

    public static void main(String args[]) throws InterruptedException {
        var latch = new CountDownLatch(1);

        var publisher = new PublishingIterable();

        var first = createPublishingThread(publisher);
        var second = createPublishingThread(publisher);
        Flux.merge(PREFETCH_SIZE, first, second)
            .parallel(2)
            .runOn(Schedulers.parallel(), PREFETCH_SIZE)
            .subscribe(new OneByOneSubscriber(latch));

        latch.await();
    }

    private static Flux<Integer> createPublishingThread(PublishingIterable publisher) {
        return Flux.fromIterable(publisher)
            .subscribeOn(Schedulers.parallel());
    }
}
