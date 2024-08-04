package com.techvg.eoffice.domain;

public class EmployeeCount {

    private long userId;
    private String employeeName;
    private long sevenDays;
    private long eightToFifteen;
    private long fifteenToThirty;
    private long oneToThreeMonth;
    private long moreThanThreeMonth;
    private long total;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getSevenDays() {
        return sevenDays;
    }

    public void setSevenDays(long sevenDays) {
        this.sevenDays = sevenDays;
    }

    public long getEightToFifteen() {
        return eightToFifteen;
    }

    public void setEightToFifteen(long eightToFifteen) {
        this.eightToFifteen = eightToFifteen;
    }

    public long getFifteenToThirty() {
        return fifteenToThirty;
    }

    public void setFifteenToThirty(long fifteenToThirty) {
        this.fifteenToThirty = fifteenToThirty;
    }

    public long getOneToThreeMonth() {
        return oneToThreeMonth;
    }

    public void setOneToThreeMonth(long oneToThreeMonth) {
        this.oneToThreeMonth = oneToThreeMonth;
    }

    public long getMoreThanThreeMonth() {
        return moreThanThreeMonth;
    }

    public void setMoreThanThreeMonth(long moreThanThreeMonth) {
        this.moreThanThreeMonth = moreThanThreeMonth;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
