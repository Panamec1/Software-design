import clock.ClockSeted;
import events.EventStatistics;
import events.EventsStatistic;
import org.junit.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;



public class TestsForClocks {
    private ClockSeted clock;
    private EventsStatistic eventsStatistic;

    @Before
    public void allBefore() {
        clock = new ClockSeted(Instant.now());
        eventsStatistic = new EventStatistics(clock);
    }

    @Test
    public void unknownEvent() {
        assertEquals(eventsStatistic.getEventStatisticByName("unknown"), 0.0, 0.0);
        assertTrue(eventsStatistic.getAllEventStatistic().isEmpty());
    }

    @Test
    public void oneEvent() {
        eventsStatistic.incEvent("name");
        eventsStatistic.incEvent("name");
        assertEquals(eventsStatistic.getEventStatisticByName("name"), 2.0 / 60.0, 0.0);
    }

    @Test
    public void oneEventDiffTime() {
        eventsStatistic.incEvent("name");
        assertEquals(eventsStatistic.getEventStatisticByName("name"), 1.0 / 60.0, 0.0);

        clock.plusMin(50);
        eventsStatistic.incEvent("name");
        assertEquals(eventsStatistic.getEventStatisticByName("name"), 2.0 / 60.0, 0.0);

        clock.plusMin(25);
        assertEquals(eventsStatistic.getEventStatisticByName("name"), 1.0 / 60.0, 0.0);
    }

    @Test
    public void statPrint() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("b");
        eventsStatistic.incEvent("b");
        eventsStatistic.printStatistic();
        assertEquals("Event: a stat:1.0\nEvent: b stat:2.0",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void someEvents() {
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("b");
        assertEquals(eventsStatistic.getEventStatisticByName("a"), 1.0 / 60.0, 0.0);
        assertEquals(eventsStatistic.getEventStatisticByName("b"), 1.0 / 60.0, 0.0);

        eventsStatistic.incEvent("a");
        assertEquals(eventsStatistic.getAllEventStatistic(), Map.of(
                "a", 2.0 / 60.0,
                "b", 1.0 / 60.0
        ));
    }

    @Test
    public void someEventWithTime() {
        eventsStatistic.incEvent("a");
        assertEquals(eventsStatistic.getEventStatisticByName("a"), 1.0 / 60.0, 0.0);
        assertEquals(eventsStatistic.getEventStatisticByName("b"), 0.0 / 60.0, 0.0);

        clock.plusMin(40);
        eventsStatistic.incEvent("b");
        assertEquals(eventsStatistic.getEventStatisticByName("a"), 1.0 / 60.0, 0.0);
        assertEquals(eventsStatistic.getEventStatisticByName("b"), 1.0 / 60.0, 0.0);

        clock.plusMin(40);
        assertEquals(eventsStatistic.getEventStatisticByName("a"), 0.0 / 60.0, 0.0);
        assertEquals(eventsStatistic.getEventStatisticByName("b"), 1.0 / 60.0, 0.0);

        assertEquals(eventsStatistic.getAllEventStatistic(), Map.of(
                "a", 0.0 / 60.0,
                "b", 1.0 / 60.0
        ));

    }


}