package com.techvg.eoffice.domain;

public class MarkerAssignedLetter {

    private String employeeName;

    public MarkerAssignedLetter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MarkerAssignedLetter(String employeeName, String inwardNumberList) {
        super();
        this.employeeName = employeeName;
        this.inwardNumberList = inwardNumberList;
    }

    private String inwardNumberList;

    public String getInwardNumberList() {
        return inwardNumberList;
    }

    public void setInwardNumberList(String inwardNumberList) {
        this.inwardNumberList = inwardNumberList;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
