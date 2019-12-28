package example.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = { "id" })
public class Book  {

    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(optional = false)
    private Author author;
}
