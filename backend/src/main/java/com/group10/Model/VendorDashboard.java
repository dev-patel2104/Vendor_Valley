package com.group10.Model;
import java.util.Map;
import java.util.Set;

public class VendorDashboard {
    /**
     * The total number of customers.
     */
    private int totalCustomers;

    /**
     * The total number of bookings.
     */
    private int totalBookings;

    /**
     * The number of accepted bookings.
     */
    private int acceptedBookings;

    /**
     * The number of completed bookings.
     */
    private int completedBookings;

    /**
     * The number of cancelled bookings.
     */
    private int cancelledBookings;

    /**
     * The number of bookings awaiting confirmation.
     */
    private int awaitingBookings;

    /**
     * The number of bookings for the current month.
     */
    private int thisMonthBookings;

    /**
     * A set of user IDs associated with the statistics.
     */
    private Set<Integer> userIds;

    /**
     * A map representing yearly activity, where keys are years and values are activity counts.
     */
    private Map<Integer, Integer> yearActivity;



    /**
     * Retrieves the map representing yearly activity.
     *
     * @return The map of years to activity counts.
     */
    public Map<Integer, Integer> getYearActivity() {
        return yearActivity;
    }

    /**
     * Sets the map representing yearly activity.
     *
     * @param yearActivity The map of years to activity counts.
     */
    public void setYearActivity(Map<Integer, Integer> yearActivity) {
        this.yearActivity = yearActivity;
    }

    /**
     * Retrieves the set of user IDs associated with the statistics.
     *
     * @return The set of user IDs.
     */
    public Set<Integer> getUserIds() {
        return userIds;
    }

    /**
     * Sets the set of user IDs associated with the statistics.
     *
     * @param userIds The set of user IDs.
     */
    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    /**
     * Retrieves the total number of customers.
     *
     * @return The total number of customers.
     */
    public int getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * Sets the total number of customers.
     *
     * @param totalCustomers The total number of customers.
     */
    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    /**
     * Retrieves the total number of bookings.
     *
     * @return The total number of bookings.
     */
    public int getTotalBookings() {
        return totalBookings;
    }

    /**
     * Sets the total number of bookings.
     *
     * @param totalBookings The total number of bookings.
     */
    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    /**
     * Retrieves the number of cancelled bookings.
     *
     * @return The number of cancelled bookings.
     */
    public int getCancelledBookings() {
        return cancelledBookings;
    }

    /**
     * Sets the number of cancelled bookings.
     *
     * @param cancelledBookings The number of cancelled bookings.
     */
    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    /**
     * Retrieves the number of awaiting bookings.
     *
     * @return The number of awaiting bookings.
     */
    public int getAwaitingBookings() {
        return awaitingBookings;
    }

    /**
     * Sets the number of awaiting bookings.
     *
     * @param awaitingBookings The number of awaiting bookings.
     */
    public void setAwaitingBookings(int awaitingBookings) {
        this.awaitingBookings = awaitingBookings;
    }

    /**
     * Retrieves the number of bookings for the current month.
     *
     * @return The number of bookings for the current month.
     */
    public int getThisMonthBookings() {
        return thisMonthBookings;
    }

    /**
     * Sets the number of bookings for the current month.
     *
     * @param thisMonthBookings The number of bookings for the current month.
     */
    public void setThisMonthBookings(int thisMonthBookings) {
        this.thisMonthBookings = thisMonthBookings;
    }

    /**
     * Retrieves the number of accepted bookings.
     *
     * @return The number of accepted bookings.
     */
    public int getAcceptedBookings() {
        return acceptedBookings;
    }

    /**
     * Sets the number of accepted bookings.
     *
     * @param acceptedBookings The number of accepted bookings.
     */
    public void setAcceptedBookings(int acceptedBookings) {
        this.acceptedBookings = acceptedBookings;
    }

    /**
     * Retrieves the number of completed bookings.
     *
     * @return The number of completed bookings.
     */
    public int getCompletedBookings() {
        return completedBookings;
    }

    /**
     * Sets the number of completed bookings.
     *
     * @param completedBookings The number of completed bookings.
     */
    public void setCompletedBookings(int completedBookings) {
        this.completedBookings = completedBookings;
    }

}
