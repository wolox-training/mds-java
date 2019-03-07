package wolox.training.models;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Book implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private String genre;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private Collection<User> users = new HashSet<>();

    /*
     * Deault constructor, required by JPA
     */

    public Book() {
    }

    /*
     * Getters and setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Preconditions.checkNotNull(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument(title != null && !title.isEmpty());
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(author != null && !author.isEmpty());
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkArgument(image != null && !image.isEmpty());
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument(subtitle != null && !subtitle.isEmpty());
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(publisher != null && !publisher.isEmpty());
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkArgument(year != null && !year.isEmpty());
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = Preconditions.checkNotNull(pages);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument(isbn != null && !isbn.isEmpty());
        this.isbn = isbn;
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users);
    }

    public void setUsers(Collection<User> users) {
        this.users = Preconditions.checkNotNull(users);
    }
}
