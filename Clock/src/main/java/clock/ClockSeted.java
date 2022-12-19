package clock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ClockSeted implements Clock {

    private  Instant now;
    public ClockSeted(Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }

    public void plusMin(int time) {
        now = now.plus(time, ChronoUnit.MINUTES);
    }


    public void setCurrent(Instant now){
        this.now = now;
    }



}
