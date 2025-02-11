package ua.andersen.library.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.andersen.library.client.AuthorClient;
import ua.andersen.library.dto.BookResponseDto;
import ua.andersen.library.dto.BookUpdateDto;
import ua.andersen.library.entity.AuthorDetails;
import ua.andersen.library.entity.Book;
import ua.andersen.library.exception.NotFoundException;
import ua.andersen.library.mapper.BookMapper;
import ua.andersen.library.repository.BookRepository;
import ua.andersen.library.service.BookService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorClient authorClient;
    private final BookMapper bookMapper;

    public Book createBook(Book book) {
        log.info("Creating a new book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Book created with ID: {}", savedBook.getId());
        return savedBook;
    }

    public List<Book> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll();
    }

    public Optional<BookResponseDto> getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            log.info("Book found with ID: {}, fetching author details", id);
            AuthorDetails authorDetails = authorClient.getAuthorDetails(book.get().getAuthor());
            return Optional.of(new BookResponseDto(book.get(), authorDetails));
        } else {
            log.warn("Book not found with ID: {}", id);
            return Optional.empty();
        }
    }

    @Transactional
    public void updateBook(Long id, BookUpdateDto dto) {
        log.info("Updating book with ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with ID: {}", id);
                    return new NotFoundException("Book not found with id: " + id);
                });

        bookMapper.updateBookFromDto(dto, book);
        bookRepository.save(book);
        log.info("Book updated with ID: {}", id);
    }

    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {
        log.info("Searching books by query: {}", query);
        return bookRepository.findByTitleContainingOrAuthorContaining(query, query);
    }

    public List<Book> findBooksPublishedAfter(int year) {
        log.info("Fetching books published after year: {}", year);
        return bookRepository.findBooksPublishedAfter(year);
    }

    public List<Object[]> getAverageRatingForEachBook() {
        log.info("Fetching average rating for each book");
        return bookRepository.getAverageRatingForEachBook();
    }

    public List<Book> findBooksWithHighRatingsSQL() {
        log.info("Fetching books with high ratings using SQL query");
        return bookRepository.findBooksWithHighRatingsSQL();
    }

    public List<Book> findBooksWithHighRatingsJPQL() {
        log.info("Fetching books with high ratings using JPQL query");
        return bookRepository.findBooksWithHighRatingsJPQL();
    }
}

