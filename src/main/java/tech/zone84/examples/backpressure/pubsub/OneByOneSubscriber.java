package tech.zone84.examples.backpressure.pubsub;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class OneByOneSubscriber implements Subscriber<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(OneByOneSubscriber.class);
    private final CountDownLatch latch;
    private Subscription subscription;

    public OneByOneSubscriber(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // 1
    }

    @Override
    public void onNext(Integer value) {
        logger.info("Consumed " + value);
        subscription.request(5); // 2
    }

    @Override
    public void onError(Throwable error) {
        logger.error("An error has occurred", error);
    }

    @Override
    public void onComplete() {
        logger.info("Reading completed");
        latch.countDown();
    }
}
