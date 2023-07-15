package com.group10.Model;

/**
 * Represents a review entity with various properties.
 */
public class Review {
    private int reviewerId;
    private int serviceId;
    private String reviewerName;
    private String reviewerCity;
    private String reviewerCountry;
    private String reviewDate;
    private String reviewTitle;
    private String reviewComment;
    private int reviewRating;
    
    public int getReviewerId() {
        return reviewerId;
    }
    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    public String getReviewerName() {
        return reviewerName;
    }
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    public String getReviewerCity() {
        return reviewerCity;
    }
    public void setReviewerCity(String reviewerCity) {
        this.reviewerCity = reviewerCity;
    }
    public String getReviewerCountry() {
        return reviewerCountry;
    }
    public void setReviewerCountry(String reviewerCountry) {
        this.reviewerCountry = reviewerCountry;
    }
    public String getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
    public String getReviewTitle() {
        return reviewTitle;
    }
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }
    public String getReviewComment() {
        return reviewComment;
    }
    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }
    public int getReviewRating() {
        return reviewRating;
    }
    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
    
}
