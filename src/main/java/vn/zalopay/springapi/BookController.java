package vn.zalopay.springapi;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.zalopay.springapi.model.Book;
import vn.zalopay.springapi.model.Response;

/**
 * </br></br>Copyright 2021, ZaloPay. All rights reserved.
 *
 * @author thoainh
 */
@RestController
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  /**
   * List all book on library
   *
   * @return Response
   */
  @GetMapping("/api/v1/books")
  public Response listBook() {
    return Response.success(bookRepository.listBook());
  }

  /**
   * Get a book by id
   *
   * @return Response
   */
  @GetMapping("/api/v1/books/{id}")
  public Response getBook(@PathVariable Integer id) {
    Optional<Book> savedBook = bookRepository.getBook(id);
    return savedBook.map(Response::success).orElseGet(Response::failed);
  }

  /**
   * Search book by title
   *
   * @return Response
   */
  @GetMapping("/api/v1/books/q")
  public Response searchBook(@RequestParam String title) {
    return Response.success(bookRepository.searchBook(title));
  }

  /**
   * Delete book by id
   *
   * @param id
   * @return
   */
  @DeleteMapping("/api/v1/books/{id}")
  public Response deleteBook(@PathVariable Integer id) {
    Optional<Book> savedBook = bookRepository.getBook(id);
    if (savedBook.isPresent()) {
      bookRepository.deleteBook(id);
      return Response.success(savedBook);
    }
    return Response.failed();
  }

  /**
   * Add new Book
   *
   * @param book
   * @return
   */
  @PostMapping("/api/v1/books")
  public Response addBook(@RequestBody Book book) {
    bookRepository.createBook(book);
    return Response.success(book);
  }

  /**
   * Edit a book
   *
   * @param id
   * @param book
   * @return
   */
  @PutMapping("/api/v1/books/{id}")
  public Response editBook(@PathVariable Integer id, @RequestBody Book book) {
    Optional<Book> savedBook = bookRepository.getBook(id);
    if (savedBook.isPresent()) {
      savedBook.get().setTitle(book.getTitle());
      savedBook.get().setDescription(book.getDescription());
      savedBook.get().setAuthor(book.getAuthor());
      bookRepository.editBook(id, savedBook.get());
      return Response.success(savedBook);
    }
    return Response.failed();
  }
}
