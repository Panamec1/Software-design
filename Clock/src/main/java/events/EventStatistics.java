package events;

import clock.Clock;
import org.apache.commons.lang3.Range;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.HOURS;

public class EventStatistics implements EventsStatistic {
    private final Clock clock;
    private final long startTime;
    private Map<String, List<Long>> listOfEvents = new HashMap<>();


    public EventStatistics(Clock clock) {
        this.clock = clock;
        startTime = clock.now().getEpochSecond();
    }

    @Override
    public void incEvent(String name) {
        listOfEvents.computeIfAbsent(name, str -> new ArrayList<>())
                .add(clock.now().getEpochSecond());
    }

    @Override
    public double getEventStatisticByName(String name) {
        if (!listOfEvents.containsKey(name)) {
            return 0;
        }
        long end = clock.now().getEpochSecond();
        long start = Math.max((end - HOURS.toSeconds(1L)), 0);
        Range<Long> timeRange = Range.between(start, end);
        double num = listOfEvents.get(name).stream().filter(timeRange::contains).count();
        double stat = num / HOURS.toMinutes(1L);
        return stat ;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Set<String> setOfKeys = listOfEvents.keySet();
        return setOfKeys.stream().collect(Collectors.toMap(Function.identity(), this::getEventStatisticByName));
    }

    @Override
    public void printStatistic() {
        long stat = (clock.now().getEpochSecond() - startTime) / HOURS.toMinutes(1L);
        long minutesLeft = Math.max(stat, 1);
        listOfEvents.forEach((key, value) -> {
            System.out.println("Event: " + key + " stat:" + (double) value.size() / minutesLeft);
        });
    }
}
