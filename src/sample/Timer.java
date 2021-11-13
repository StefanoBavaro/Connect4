package sample;
import java.time.Duration;

/**
 * Knows:
 *      <ul>
 *          <li>Il momento in cui viene avviato un timer</li>
 *          <li>Il momento in cui viene fermato un timer</li>
 *          <li>Il tempo totale trascorso dalla prima attivazione di un timer</li>
 *      </ul>
 * Does:
 *       <ul>
 *          <li>Fa partire un timer</li>
 *          <li>Ferma un timer</li>
 *          <li>Restituisce il tempo trascorso di un timer</li>
 *          <li>Rende stampabile un intervallo di tempo</li>
 *       </ul>
 *
 * @author Gruppo Ellison
 */

public class Timer {
    /**
     * <code> startingTime </code> rappresenta il tempo di inizio della misurazione
     *
     */
    private long startingTime;

    /**
     * <code> endingTime </code> rappresenta il tempo trascorso tra un avvio e un'interruzione del timer
     *
     */
    private long endingTime;

    /**
     * <code> totalTime </code> rappresenta il tempo totale trascorso dall'avvio del timer
     *
     */
    private long totalTime;

    /**
     * Metodo di set dello startingTime
     *
     * @param start il valore da inserire nell'attributo startingTime
     *
     */
    private void setStartingTime(final long start) {
        startingTime = start;
    }

    /**
    * Metodo che restituisce lo startingTime
    *
    * @return il valore letto dall'attributo startingTime
    *
    */
    private long getStartingTime() {
        return startingTime;
    }

    /**
     * Metodo di set dell'endingTime
     *
     * @param end il valore da inserire nell'attributo endingTime
     *
     */
    private void setEndingTime(final long end) {
        endingTime = end;
    }

    /**
     * Metodo che restituisce l'endingTime
     *
     * @return il valore letto dall'attributo endingTime
     *
     */
    private long getEndingTime() {
        return endingTime;
    }

    /**
     * Metodo di set del totalTime
     *
     * @param total il valore da inserire nell'attributo totalTime
     *
     */
    private void setTotalTime(final long total) {
        totalTime = total;
    }

    /**
     * Metodo che restituisce il totalTime
     *
     * @return il valore letto dall'attributo totalTime
     *
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Metodo che memorizza il tempo di inizializzazione del time
     *
     *
     */
    public void start() {
        setStartingTime(System.nanoTime());
    }

    /**
     * Metodo che memorizza il tempo trascorso dall'inizio della misurazione
     *
     *
     */
    public void stop() {
        setEndingTime(System.nanoTime());
        setTotalTime(getTotalTime() + (getEndingTime() - getStartingTime()));
    }

    /**
     * Metodo che restituisce il tempo trascorso in formato stringa
     *
     * @return il tempo trascorso sotto forma di stringa
     */
    public String elapsed() {
        if (getStartingTime() > getEndingTime()) {
            setTotalTime(getTotalTime() + (System.nanoTime() - getStartingTime()));
            setStartingTime(System.nanoTime());
            return humanReadableFormat(Duration.ofSeconds(getTotalTime() / Constants.TIME_CONSTANT));
        } else {
            return humanReadableFormat(Duration.ofSeconds(getTotalTime() / Constants.TIME_CONSTANT));
        }
    }

    /**
     * Metodo che converte un oggetto Duration in una stringa
     *
     * @return il parametro duration sotto forma di stringa
     */
    private static String humanReadableFormat(final Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}






