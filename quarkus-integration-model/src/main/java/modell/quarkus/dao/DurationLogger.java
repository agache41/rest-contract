package modell.quarkus.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

@Transactional
@RequestScoped
public class DurationLogger {
    private static final Boolean ENABLED = true;
    private static final Logger logger = Logger.getLogger(DurationLogger.class);
    private static final Map<String, Long> START_TIMES = new HashMap<>();

    public void start(String context) {
        if (ENABLED) {
            Long startTime = System.currentTimeMillis();
            START_TIMES.put(context, startTime);
            logger.info("Starting " + context);
        }
    }

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

    public void reset() {
        START_TIMES.clear();
    }
}
