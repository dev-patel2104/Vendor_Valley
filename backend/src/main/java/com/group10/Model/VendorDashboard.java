package com.group10.Model;
import java.util.Map;
import java.util.Set;

public class VendorDashboard {
    private int totalCustomers;
    private int totalBookings;
    private int acceptedBookings;
    private int completedBookings;
    private int cancelledBookings;
    private int awaitingBookings;
    private int thisMonthBookings;
    private Set<Integer> userIds; 
    private Map<Integer, Integer> yearActivity;


    public Map<Integer, Integer> getYearActivity() {
        return yearActivity;
    }
    public void setYearActivity(Map<Integer, Integer> yearActivity) {
        this.yearActivity = yearActivity;
    }
    public Set<Integer> getUserIds() {
        return userIds;
    }
    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }
    public int getTotalCustomers() {
        return totalCustomers;
    }
    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
    public int getTotalBookings() {
        return totalBookings;
    }
    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }
    public int getCancelledBookings() {
        return cancelledBookings;
    }
    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }
    public int getAwaitingBookings() {
        return awaitingBookings;
    }
    public void setAwaitingBookings(int awaitingBookings) {
        this.awaitingBookings = awaitingBookings;
    }
    public int getThisMonthBookings() {
        return thisMonthBookings;
    }
    public void setThisMonthBookings(int thisMonthBookings) {
        this.thisMonthBookings = thisMonthBookings;
    }
    public int getAcceptedBookings() {
        return acceptedBookings;
    }
    public void setAcceptedBookings(int acceptedBookings) {
        this.acceptedBookings = acceptedBookings;
    }
    public int getCompletedBookings() {
        return completedBookings;
    }
    public void setCompletedBookings(int completedBookings) {
        this.completedBookings = completedBookings;
    }

}
