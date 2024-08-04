package com.techvg.eoffice.domain;

public class DashboardCount {

    private long recentlyAdded;
    private long Pending;
    private long awaiting;
    private long hearing;
    private long cleared;
    private long todaysHearing;
    private long totalPending;

    public long getPending() {
        return Pending;
    }

    public void setPending(long pending) {
        Pending = pending;
    }

    public long getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(long recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public long getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(long totalPending) {
        this.totalPending = totalPending;
    }

    public long getAwaiting() {
        return awaiting;
    }

    public void setAwaiting(long awaiting) {
        this.awaiting = awaiting;
    }

    public long getHearing() {
        return hearing;
    }

    public void setHearing(long hearing) {
        this.hearing = hearing;
    }

    public long getCleared() {
        return cleared;
    }

    public void setCleared(long cleared) {
        this.cleared = cleared;
    }

    public long getTodaysHearing() {
        return todaysHearing;
    }

    public void setTodaysHearing(long todaysHearing) {
        this.todaysHearing = todaysHearing;
    }
}
