package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements BaseEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate birthdate;

  @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  private Collection<Book> books = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = Preconditions.checkNotNull(id);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    Preconditions.checkArgument(username != null && !username.isEmpty());
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Preconditions.checkArgument(name != null && !name.isEmpty());
    this.name = name;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = Preconditions.checkNotNull(birthdate);
  }

  public Collection<Book> getBooks() {
    return Collections.unmodifiableCollection(books);
  }

  public void setBooks(Collection<Book> books) {
    this.books = Preconditions.checkNotNull(books);
  }

  public boolean addBook(Book book) {
    Preconditions.checkNotNull(book);
    return books.add(book);
  }

  public boolean removeBook(Book book) {
    Preconditions.checkNotNull(book);
    return books.remove(book);
  }
}
