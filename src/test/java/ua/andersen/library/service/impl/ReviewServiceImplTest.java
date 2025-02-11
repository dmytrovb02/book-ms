package ua.andersen.library.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.andersen.library.dto.ReviewRequestDto;
import ua.andersen.library.entity.Book;
import ua.andersen.library.entity.Review;
import ua.andersen.library.exception.NotFoundException;
import ua.andersen.library.repository.BookRepository;
import ua.andersen.library.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Book book;
    private Review review;
    private ReviewRequestDto reviewRequestDto;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Book", "Test Author", 2020, 5, List.of(), false);
        review = new Review(1L, 5, "Great book!", book, false);
        reviewRequestDto = new ReviewRequestDto(1L, 5, "Great book!");
    }

    @Test
    void addReview_ShouldReturnSavedReview() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(reviewRequestDto);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getRating()).isEqualTo(5);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void addReview_ShouldThrowException_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.addReview(reviewRequestDto));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getReviewsByBookId_ShouldReturnListOfReviews() {
        when(reviewRepository.findByBookId(1L)).thenReturn(List.of(review));

        List<Review> reviews = reviewService.getReviewsByBookId(1L);

        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getComment()).isEqualTo("Great book!");
        verify(reviewRepository).findByBookId(1L);
    }

    @Test
    void updateReview_ShouldReturnUpdatedReview() {
        when(reviewRepository.save(review)).thenReturn(review);

        Review updatedReview = reviewService.updateReview(review);

        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getRating()).isEqualTo(5);
        verify(reviewRepository).save(review);
    }

    @Test
    void deleteReview_ShouldCallRepositoryDeleteById() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository).deleteById(1L);
    }
}
