package ua.andersen.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.andersen.library.dto.BookResponseDto;
import ua.andersen.library.dto.BookUpdateDto;
import ua.andersen.library.entity.Book;
import ua.andersen.library.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book", description = "Adds a new book to the library")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves a list of all available books")
    @ApiResponse(responseCode = "200", description = "List of books retrieved successfully")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public Optional<BookResponseDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book's details")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto dto) {
        bookService.updateBook(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Removes a book by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Finds books matching a search query")
    @ApiResponse(responseCode = "200", description = "List of matching books")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @GetMapping("/published-after")
    @Operation(summary = "Find books published after a given year", description = "Retrieves books published after the specified year")
    @ApiResponse(responseCode = "200", description = "List of books published after the given year")
    public List<Book> findBooksPublishedAfter(@RequestParam int year) {
        return bookService.findBooksPublishedAfter(year);
    }

    @GetMapping("/average-rating")
    @Operation(summary = "Get average ratings", description = "Retrieves the average rating for each book")
    @ApiResponse(responseCode = "200", description = "List of books with their average ratings")
    public List<Object[]> getAverageRatingForEachBook() {
        return bookService.getAverageRatingForEachBook();
    }

    @GetMapping("/high-ratings/sql")
    @Operation(summary = "Find books with high ratings (SQL)", description = "Retrieves books with high ratings using a native SQL query")
    @ApiResponse(responseCode = "200", description = "List of books with high ratings")
    public List<Book> findBooksWithHighRatingsSQL() {
        return bookService.findBooksWithHighRatingsSQL();
    }

    @GetMapping("/high-ratings/jpql")
    @Operation(summary = "Find books with high ratings (JPQL)", description = "Retrieves books with high ratings using a JPQL query")
    @ApiResponse(responseCode = "200", description = "List of books with high ratings")
    public List<Book> findBooksWithHighRatingsJPQL() {
        return bookService.findBooksWithHighRatingsJPQL();
    }
}
