package tech.zone84.examples.backpressure.pubsub;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitedSubscriber implements Subscriber<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(LimitedSubscriber.class);

    private final int limit;
    private final CountDownLatch latch;
    private final AtomicInteger received = new AtomicInteger(0);
    private Subscription subscription;

    public LimitedSubscriber(int limit, CountDownLatch latch) {
        this.limit = limit;
        this.latch = latch;
    }

    @Override
    public void onSubscribe(Subscription s) {
        logger.info("Requesting " + limit + " elements...");
        subscription = s;
        subscription.request(limit);
        subscription.request(limit);
    }

    @Override
    public void onNext(Integer value) {
        var index = received.incrementAndGet();
        logger.info("Received element #" + index + ": " + value);

        if (index == limit) {
            subscription.cancel();
            latch.countDown();
        }
    }

    @Override
    public void onError(Throwable error) {
        logger.error("Panic!", error);
        latch.countDown();
    }

    @Override
    public void onComplete() {
        logger.info("Completed.");
        latch.countDown();
    }
}
