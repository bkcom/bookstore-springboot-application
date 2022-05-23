package vn.bkcom.vlerapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.bkcom.vlerapp.model.Book;

/**
 * </br></br>Copyright 2021, ZaloPay. All rights reserved.
 *
 * @author thoainh
 */
@Repository
public class BookRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;


  /**
   * CreateBook
   * @param book
   * @return ID
   */
  public int createBook(Book book) {
    return jdbcTemplate
        .update("INSERT INTO book(title, description, author) VALUES (?, ?, ?)", book.getTitle(),
            book.getDescription(), book.getAuthor());
  }

  /**
   * DeleteBook
   * @param id
   * @return ID
   */
  public int deleteBook(Integer id) {
    return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
  }

  /**
   * EditBook
   * @param id
   * @return ID
   */
  public int editBook(Integer id, Book book) {
    return jdbcTemplate
        .update("UPDATE book SET title = ?, description = ?, author = ? WHERE id = ?",
            book.getTitle(), book.getDescription(), book.getAuthor(), id);
  }

  /**
   * GetBook
   * @param id
   * @return Book
   */
  public Optional<Book> getBook(Integer id) {
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?",
          (rs, rowNum) -> Optional.of(mapRow(rs)), id);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * SearchBook
   * @param title
   * @return List Book
   */
  public List<Book> searchBook(String title) {
      return jdbcTemplate.query("SELECT * FROM book WHERE title like ?",
          (rs, rowNum) -> mapRow(rs), "%" + title + "%");
  }

  /**
   * ListBook
   * @return List Book
   */
  public List<Book> listBook() {
    return jdbcTemplate.query("SELECT * FROM book", (rs, rowNum) -> mapRow(rs));
  }

  public Book mapRow(ResultSet rs) throws SQLException {
    Book book = new Book();
    book.setId(rs.getInt("id"));
    book.setTitle(rs.getString("title"));
    book.setDescription(rs.getString("description"));
    book.setAuthor(rs.getString("author"));
    return book;
  }

}
