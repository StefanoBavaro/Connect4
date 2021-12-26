import java.time.Duration;

public class Timer {

    private long startingTime;

    private long endingTime;

    private long totalTime;

    private void setStartingTime(final long start) {
        startingTime = start;
    }

    private long getStartingTime() {
        return startingTime;
    }

    private void setEndingTime(final long end) {
        endingTime = end;
    }

    private long getEndingTime() {
        return endingTime;
    }

    private void setTotalTime(final long total) {
        totalTime = total;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void start() {
        setStartingTime(System.nanoTime());
    }

    public void stop() {
        setEndingTime(System.nanoTime());
        setTotalTime(getTotalTime() + (getEndingTime() - getStartingTime()));
    }

    public String elapsed() {
        if (getStartingTime() > getEndingTime()) {
            setTotalTime(getTotalTime() + (System.nanoTime() - getStartingTime()));
            setStartingTime(System.nanoTime());
            return humanReadableFormat(Duration.ofSeconds(getTotalTime() / Constants.TIME_CONSTANT));
        } else {
            return humanReadableFormat(Duration.ofSeconds(getTotalTime() / Constants.TIME_CONSTANT));
        }
    }

    private static String humanReadableFormat(final Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}






