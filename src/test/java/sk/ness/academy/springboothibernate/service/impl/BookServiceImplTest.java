package sk.ness.academy.springboothibernate.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import sk.ness.academy.springboothibernate.dao.BookDao;
import sk.ness.academy.springboothibernate.dao.impl.BookDaoImpl;
import sk.ness.academy.springboothibernate.dto.BookDto;
import sk.ness.academy.springboothibernate.model.Book;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookServiceImplTest {
  @Mock
  private BookDao bookDao;

  @InjectMocks
  private BookServiceImpl bookService;

  private List<Book> books;

  @Test
  void testFindAll() {
    Mockito.when(bookDao.findAll()).thenReturn(books);

    final List<BookDto> bookDtos = bookService.findAll();

    Assertions.assertEquals(2, bookDtos.size());
    Assertions.assertEquals("X_Book_1", bookDtos.get(0).getName());
    Assertions.assertEquals("X_Book_2", bookDtos.get(1).getName());
  }

  @Test
  void testFindAllEmpty() {
    Mockito.when(bookDao.findAll()).thenReturn(new ArrayList<>());

    final List<BookDto> bookDtos = bookService.findAll();

    Assertions.assertEquals(0, bookDtos.size());
  }


  @Test
  void testFindAllNull() {
    Mockito.when(bookDao.findAll()).thenReturn(null);

    final List<BookDto> bookDtos = bookService.findAll();

    Assertions.assertTrue(bookDtos.isEmpty());
  }

  @BeforeEach
  private void init() {
    final Book book1 = new Book();
    book1.setName("Book_1");
    book1.setId(1L);

    final Book book2 = new Book();
    book2.setName("Book_2");
    book2.setId(2L);

    books = new ArrayList<>();
    books.add(book1);
    books.add(book2);
  }


  @Test
  void verifySaveIsCalled() {
      final Book created = new Book();
      created.setName("Test book");
      bookService.save(created);
      Mockito.verify(bookDao, Mockito.times(1)).persist(created);
  }

  @Test
  void verifySaveIsNull() {
    bookService.save(null);
    Mockito.verify(bookDao, Mockito.times(1)).persist(null);
  }


}