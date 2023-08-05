package com.group10.Model;

/**
 * Represents a review entity with various properties.
 */
public class Review {
    private int reviewerId;
    private int serviceId;
    private int bookingId;
    private String reviewerName;
    private String reviewerCity;
    private String reviewerCountry;
    private String reviewDate;
    private String reviewTitle;
    private String reviewComment;
    private int reviewRating;

    /**
     * Get the booking ID.
     *
     * @return The booking ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Set the booking ID.
     *
     * @param bookingId The booking ID to set.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Get the reviewer ID.
     *
     * @return The reviewer ID.
     */
    public int getReviewerId() {
        return reviewerId;
    }

    /**
     * Set the reviewer ID.
     *
     * @param reviewerId The reviewer ID to set.
     */
    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * Get the service ID.
     *
     * @return The service ID.
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Set the service ID.
     *
     * @param serviceId The service ID to set.
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Get the reviewer's name.
     *
     * @return The reviewer's name.
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * Set the reviewer's name.
     *
     * @param reviewerName The reviewer's name to set.
     */
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    /**
     * Get the reviewer's city.
     *
     * @return The reviewer's city.
     */
    public String getReviewerCity() {
        return reviewerCity;
    }

    /**
     * Set the reviewer's city.
     *
     * @param reviewerCity The reviewer's city to set.
     */
    public void setReviewerCity(String reviewerCity) {
        this.reviewerCity = reviewerCity;
    }

    /**
     * Get the reviewer's country.
     *
     * @return The reviewer's country.
     */
    public String getReviewerCountry() {
        return reviewerCountry;
    }

    /**
     * Set the reviewer's country.
     *
     * @param reviewerCountry The reviewer's country to set.
     */
    public void setReviewerCountry(String reviewerCountry) {
        this.reviewerCountry = reviewerCountry;
    }

    /**
     * Get the review date.
     *
     * @return The review date.
     */
    public String getReviewDate() {
        return reviewDate;
    }

    /**
     * Set the review date.
     *
     * @param reviewDate The review date to set.
     */
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * Get the review title.
     *
     * @return The review title.
     */
    public String getReviewTitle() {
        return reviewTitle;
    }

    /**
     * Set the review title.
     *
     * @param reviewTitle The review title to set.
     */
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    /**
     * Get the review comment.
     *
     * @return The review comment.
     */
    public String getReviewComment() {
        return reviewComment;
    }

    /**
     * Set the review comment.
     *
     * @param reviewComment The review comment to set.
     */
    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    /**
     * Get the review rating.
     *
     * @return The review rating.
     */
    public int getReviewRating() {
        return reviewRating;
    }

    /**
     * Set the review rating.
     *
     * @param reviewRating The review rating to set.
     */
    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    /**
     * Generates a string representation of the Review object.
     *
     * @return A string containing the details of a review.
     */
    @Override
    public String toString() {
        return "Review{" +
                "reviewerId=" + reviewerId +
                ", serviceId=" + serviceId +
                ", bookingId=" + bookingId +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewerCity='" + reviewerCity + '\'' +
                ", reviewerCountry='" + reviewerCountry + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewComment='" + reviewComment + '\'' +
                ", reviewRating=" + reviewRating +
                '}';
    }
}
