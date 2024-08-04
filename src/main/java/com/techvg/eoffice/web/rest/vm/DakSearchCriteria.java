package com.techvg.eoffice.web.rest.vm;

public class DakSearchCriteria {

    private String inwardNoTo;
    private String inwardNoFrom;
    private Long organizationId;
    private Long dakAssignedfromId;
    private Long dakAssignedToId;

    public String getInwardNoTo() {
        return inwardNoTo;
    }

    public void setInwardNoTo(String inwardNoTo) {
        this.inwardNoTo = inwardNoTo;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDakAssignedfromId() {
        return dakAssignedfromId;
    }

    public void setDakAssignedfromId(Long dakAssignedfromId) {
        this.dakAssignedfromId = dakAssignedfromId;
    }

    public String getInwardNoFrom() {
        return inwardNoFrom;
    }

    public void setInwardNoFrom(String inwardNoFrom) {
        this.inwardNoFrom = inwardNoFrom;
    }

    public Long getDakAssignedToId() {
        return dakAssignedToId;
    }

    public void setDakAssignedToId(Long dakAssignedToId) {
        this.dakAssignedToId = dakAssignedToId;
    }
}
