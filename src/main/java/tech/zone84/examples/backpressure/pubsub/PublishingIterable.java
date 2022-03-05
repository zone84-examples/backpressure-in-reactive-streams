package tech.zone84.examples.backpressure.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class PublishingIterable implements Iterable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(PublishingIterable.class);
    private final Iterator<Integer> iterator = new PublishingIterator();

    @Override
    public Iterator<Integer> iterator() {
        return iterator;
    }

    class PublishingIterator implements Iterator<Integer> {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final AtomicInteger generator = new AtomicInteger(0);

        @Override
        public boolean hasNext() {
            return counter.getAndIncrement() < 1000;
        }

        @Override
        public Integer next() {
            var value = generator.getAndIncrement();
            logger.info("Produced value: " + value);
            return value;
        }
    }
}
