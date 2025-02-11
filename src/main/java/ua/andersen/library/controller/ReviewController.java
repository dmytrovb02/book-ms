package ua.andersen.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.andersen.library.dto.ReviewRequestDto;
import ua.andersen.library.entity.Review;
import ua.andersen.library.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@Tag(name = "Reviews", description = "Operations related to book reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Add a review", description = "Adds a new review for a book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid review data")
    })
    public Review addReview(@RequestBody ReviewRequestDto review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get reviews for a book", description = "Retrieves all reviews for a specific book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public List<Review> getReviewsByBookId(@PathVariable Long bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review", description = "Updates an existing review")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        review.setId(id);
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Removes a review by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
