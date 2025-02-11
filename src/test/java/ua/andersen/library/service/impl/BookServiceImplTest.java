package ua.andersen.library.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.andersen.library.client.AuthorClient;
import ua.andersen.library.dto.BookResponseDto;
import ua.andersen.library.dto.BookUpdateDto;
import ua.andersen.library.entity.AuthorDetails;
import ua.andersen.library.entity.Book;
import ua.andersen.library.mapper.BookMapper;
import ua.andersen.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorClient authorClient;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Title", "Test Author", 2020, 5, List.of(), false);
    }

    @Test
    void createBook_ShouldSaveBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals("Test Title", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getAllBooks_ShouldReturnBookList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> books = bookService.getAllBooks();

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBookResponseDto() {
        AuthorDetails authorDetails = new AuthorDetails("Test Author", "Bio");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorClient.getAuthorDetails("Test Author")).thenReturn(authorDetails);

        Optional<BookResponseDto> bookDto = bookService.getBookById(1L);

        assertTrue(bookDto.isPresent());
        assertEquals("Test Title", bookDto.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(authorClient, times(1)).getAuthorDetails("Test Author");
    }

    @Test
    void updateBook_ShouldUpdateExistingBook() {
        BookUpdateDto updateDto = new BookUpdateDto();
        updateDto.setTitle("Updated Title");
        updateDto.setPublicationYear(2022);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.updateBook(1L, updateDto);

        verify(bookMapper, times(1)).updateBookFromDto(updateDto, book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBook_ShouldCallRepositoryDeleteById() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchBooks_ShouldReturnMatchingBooks() {
        when(bookRepository.findByTitleContainingOrAuthorContaining("Test", "Test"))
                .thenReturn(List.of(book));

        List<Book> books = bookService.searchBooks("Test");

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findByTitleContainingOrAuthorContaining("Test", "Test");
    }

    @Test
    void findBooksPublishedAfter_ShouldReturnBooks() {
        when(bookRepository.findBooksPublishedAfter(2019)).thenReturn(List.of(book));

        List<Book> books = bookService.findBooksPublishedAfter(2019);

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findBooksPublishedAfter(2019);
    }
}
