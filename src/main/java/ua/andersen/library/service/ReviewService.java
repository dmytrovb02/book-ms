package ua.andersen.library.service;

import ua.andersen.library.dto.ReviewRequestDto;
import ua.andersen.library.entity.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(ReviewRequestDto review);

    List<Review> getReviewsByBookId(Long bookId);

    Review updateReview(Review review);

    void deleteReview(Long id);
}
