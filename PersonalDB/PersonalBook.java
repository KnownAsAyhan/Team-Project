package PersonalDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonalBook {
    private String id;
    private String title;
    private String genre;
    private String author;
    private List<String> userReviews;
    private String status;
    private int timeSpent;
    private List<Double> userRatings;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PersonalBook(String title, String author) {
        this.title = title;
        this.author = author;
        this.userReviews = new ArrayList<>();
        this.status = "Not Started";
        this.timeSpent = 0;
        this.userRatings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getUserReviews() {
        return userReviews;
    }

    public void addUserReview(String review) {
        if (review != null && !review.isEmpty()) {
            userReviews.add(review);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void addTimeSpent(int time) {
        if (time > 0) {
            this.timeSpent += time;
        }
    }

    public List<Double> getUserRatings() {
        return userRatings;
    }

    public void addUserRating(double rating) {
        if (rating >= 1 && rating <= 5) {
            userRatings.add(rating);
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void rateBook(double rating) {
        addUserRating(rating);
        // Update status or perform any other actions as needed
    }

    public void stopTimer() {
        // Implement stopTimer logic
        // Calculate elapsed time and update timeSpent
        if (startTime != null && endTime == null) {
            endTime = LocalDateTime.now();
            timeSpent += calculateElapsedTime(startTime, endTime);
        }
    }

    private int calculateElapsedTime(LocalDateTime start, LocalDateTime end) {
        // Calculate elapsed time in minutes (for simplicity)
        return (int) java.time.Duration.between(start, end).toMinutes();
    }
}
