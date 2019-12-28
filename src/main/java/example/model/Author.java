package example.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = { "id" })
public class Author {

    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Book> books;

}
