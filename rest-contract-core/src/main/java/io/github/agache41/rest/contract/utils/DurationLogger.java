package io.github.agache41.rest.contract.utils;

import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Duration logger.
 */
public class DurationLogger {
    private static final Boolean ENABLED = true;
    private static final Logger logger = Logger.getLogger(DurationLogger.class);
    private static final Map<String, Long> START_TIMES = new HashMap<>();

    /**
     * Start.
     *
     * @param context the context
     */
    public void start(String context) {
        if (ENABLED) {
            Long startTime = System.currentTimeMillis();
            START_TIMES.put(context, startTime);
            logger.info("Starting " + context);
        }
    }

    /**
     * Finish.
     *
     * @param context the context
     */
    public void finish(String context) {
        if (ENABLED) {
            Long finishTime = System.currentTimeMillis();
            Long startTime = START_TIMES.get(context);
            if (startTime == null) {
                logger.info("Finished " + context + ": without start timestamp.");
            } else {
                START_TIMES.remove(context);
                Long duration = finishTime - startTime;
                logger.info("Finished " + context + ":took " + duration + "ms.");
            }
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        START_TIMES.clear();
    }
}
