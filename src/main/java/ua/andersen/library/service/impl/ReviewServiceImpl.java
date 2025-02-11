package ua.andersen.library.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.andersen.library.dto.ReviewRequestDto;
import ua.andersen.library.entity.Book;
import ua.andersen.library.entity.Review;
import ua.andersen.library.exception.NotFoundException;
import ua.andersen.library.repository.BookRepository;
import ua.andersen.library.repository.ReviewRepository;
import ua.andersen.library.service.ReviewService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public Review addReview(ReviewRequestDto dto) {
        log.info("Adding review for book ID: {}", dto.getBookId());

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> {
                    log.warn("Book with ID {} not found", dto.getBookId());
                    return new NotFoundException("Book with ID " + dto.getBookId() + " not found");
                });

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setBook(book);

        Review savedReview = reviewRepository.save(review);
        log.info("Review added with ID: {}", savedReview.getId());
        return savedReview;
    }

    public List<Review> getReviewsByBookId(Long bookId) {
        log.info("Fetching reviews for book ID: {}", bookId);
        return reviewRepository.findByBookId(bookId);
    }

    public Review updateReview(Review review) {
        log.info("Updating review with ID: {}", review.getId());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        log.info("Deleting review with ID: {}", id);
        reviewRepository.deleteById(id);
    }
}
