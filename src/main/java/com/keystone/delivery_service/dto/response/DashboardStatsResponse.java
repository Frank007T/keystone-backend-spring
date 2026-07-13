package com.keystone.delivery_service.dto.response;

public class DashboardStatsResponse {

    private long customers;
    private long sites;
    private long workOrders;
    private long technicians;

    public DashboardStatsResponse(
            long customers,
            long sites,
            long workOrders,
            long technicians) {

        this.customers = customers;
        this.sites = sites;
        this.workOrders = workOrders;
        this.technicians = technicians;
    }

    public long getCustomers() {
        return customers;
    }

    public void setCustomers(long customers) {
        this.customers = customers;
    }

    public long getSites() {
        return sites;
    }

    public void setSites(long sites) {
        this.sites = sites;
    }

    public long getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(long workOrders) {
        this.workOrders = workOrders;
    }

    public long getTechnicians() {
        return technicians;
    }

    public void setTechnicians(long technicians) {
        this.technicians = technicians;
    }
}