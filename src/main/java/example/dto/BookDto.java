package example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @NotNull(message = "Genre is mandatory")
    private String genre;

    private BigDecimal price;

    @NotNull(message = "Author is mandatory")
    private AuthorDto author;
}
