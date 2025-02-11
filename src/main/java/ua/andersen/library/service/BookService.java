package ua.andersen.library.service;

import ua.andersen.library.dto.BookResponseDto;
import ua.andersen.library.dto.BookUpdateDto;
import ua.andersen.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(Book book);

    List<Book> getAllBooks();

    Optional<BookResponseDto> getBookById(Long id);

    void updateBook(Long id, BookUpdateDto dto);

    void deleteBook(Long id);

    List<Book> searchBooks(String query);

    List<Book> findBooksPublishedAfter(int year);

    List<Object[]> getAverageRatingForEachBook();

    List<Book> findBooksWithHighRatingsSQL();

    List<Book> findBooksWithHighRatingsJPQL();
}
