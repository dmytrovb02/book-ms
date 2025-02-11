package ua.andersen.library.dto;

import lombok.Data;

@Data
public class BookUpdateDto {
    private String title;
    private String author;
    private Integer publicationYear;
    private Integer availableCopies;

}
