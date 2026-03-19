package model.entities.enemies;

/**
 * Encapsulates a fixed-duration cooldown timer.
 *
 * <p>Originally a pair of bare fields (isStopped / stopStartTime) on Enemy.
 * Extracted so the concept has one home, the fields can't drift out of sync,
 * and the same logic can be reused for the player's shock state with a different
 * duration (1_000_000_000L vs 500_000_000L).
 *
 * <p>The timer self-clears: once {@link #isActive()} detects that the duration
 * has elapsed it resets its own state, so callers never manually cancel it.
 */
// Note: lives in the enemies package for now; move to model.entities if further
// classes outside this package need it.
public final class StunTimer {

    private final long durationNs;

    private boolean active      = false;
    private long    startTimeNs = 0L;

    /**
     * @param durationNs timer length in nanoseconds (use {@code System.nanoTime()} units)
     */
    public StunTimer(long durationNs) {
        this.durationNs = durationNs;
    }

    /** Starts (or restarts) the timer from right now. */
    public void start() {
        active      = true;
        startTimeNs = System.nanoTime();
    }

    /**
     * Returns {@code true} if the timer is running and has not yet elapsed.
     * Automatically clears when the duration passes.
     */
    public boolean isActive() {
        if (!active) return false;
        if (System.nanoTime() - startTimeNs >= durationNs) {
            active = false;
            return false;
        }
        return true;
    }
}