package ua.andersen.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.andersen.library.entity.AuthorDetails;
import ua.andersen.library.entity.Book;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
    private Integer availableCopies;
    private AuthorDetails authorDetails;

    public BookResponseDto(Book book, AuthorDetails authorDetails) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicationYear = book.getPublicationYear();
        this.availableCopies = book.getAvailableCopies();
        this.authorDetails = authorDetails;
    }
}
