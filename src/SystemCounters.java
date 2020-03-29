import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SystemCounters {

    protected final HashMap<String, Long> eventTypeCount = new HashMap<>();
    protected final HashMap<String, Long> wordCount = new HashMap<>();

    protected long errorCounter = 0;
    protected long totalLinesCounter = 0;
    protected long validJsonCounter = 0;
    protected long invalidJsonCounter = 0;
    protected long missingEventTypeCounter = 0;
    protected long missingDataCounter = 0;

    public SystemCounters() {
    }

    public long getTotalLinesCounter() { return totalLinesCounter; }
    public long getValidJsonCounter() { return validJsonCounter;}
    public long getInvalidJsonCounter() { return invalidJsonCounter;}
    public long getMissingEventTypeCounter() {
        return missingEventTypeCounter;
    }
    public long getMissingDataCounter() {
        return missingDataCounter;
    }
    public long getErrorCounter() {
        return errorCounter;
    }

    public long getEventCount(final String eventType) {
        if (eventTypeCount.containsKey(eventType)) {
            return eventTypeCount.get(eventType);
        }
        return 0;
    }

    public void updateEventCount(final String eventType) {
        if (eventType == null) {
            incrementMissingEventType();
        }
        long current = getEventCount(eventType);
        eventTypeCount.put(eventType, ++current);
    }

    public long getWordCount(final String word) {
        if (wordCount.containsKey(word)) {
            return wordCount.get(word);
        }
        return 0;
    }

    public void updateWordCount(final String [] words) {
        if (words == null) {
            incrementMissingData();
            return;
        }
        for (String word : words) {
            long current = getWordCount(word);
            wordCount.put(word, ++current);
        }
    }

    public void incrementTotalLines() { ++totalLinesCounter; }
    public void incrementValidJson() {
        ++validJsonCounter;
    }
    public void incrementInvalidJson() { ++invalidJsonCounter;}

    public void incrementMissingEventType() {
        ++missingEventTypeCounter;
    }

    public void incrementMissingData() {
        ++missingDataCounter;
    }

    public long incrementErrorCounter() {
        return ++errorCounter;
    }

    public StringBuilder generateStatsReport() {

        StringBuilder stats = new StringBuilder("Current counters:\n");
        stats.append("==================\n");

        long totalEventTypes = 0;
        Iterator<Map.Entry<String, Long>> iterator = eventTypeCount.entrySet().iterator();
        stats.append("\tEvent Type counters:\n");
        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = iterator.next();
            long value = entry.getValue();
            totalEventTypes += value;
            stats.append("\t\t").append(entry.getKey()).append(": ").append(value);
            stats.append("\n");
        }
        stats.append("\t\ttotal: ").append(totalEventTypes).append("\n\n");

        iterator = wordCount.entrySet().iterator();
        stats.append("\tword counters:\n");
        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = iterator.next();
            stats.append("\t\t").append(entry.getKey()).append(": ").append(entry.getValue());
            stats.append("\n");
        }
        stats.append("\n");

        stats.append("\tvalid json lines: ").append(getValidJsonCounter()).append("\n");
        stats.append("\tinvalid json lines: ").append(getInvalidJsonCounter()).append("\n");
        stats.append("\ttotal input lines: ").append(getTotalLinesCounter()).append("\n");

        return stats;
    }
}
