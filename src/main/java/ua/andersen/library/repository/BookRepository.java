package ua.andersen.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.andersen.library.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingOrAuthorContaining(String title, String author);

    @Query(value = "SELECT * FROM books WHERE publication_year > :year", nativeQuery = true)
    List<Book> findBooksPublishedAfter(int year);

    @Query("SELECT b.title, AVG(r.rating) FROM Book b JOIN b.reviews r GROUP BY b.title")
    List<Object[]> getAverageRatingForEachBook();

    @Query(value = "SELECT * FROM books b WHERE (SELECT AVG(r.rating) FROM reviews r WHERE r.book_id = b.id) >= 4", nativeQuery = true)
    List<Book> findBooksWithHighRatingsSQL();

    @Query("SELECT b FROM Book b WHERE (SELECT AVG(r.rating) FROM Review r WHERE r.book = b) >= 4")
    List<Book> findBooksWithHighRatingsJPQL();
}
