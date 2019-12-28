package example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorDto {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "An alphanumeric first name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "An alphanumeric last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "A valid mail address is mandatory")
    private String email;
}
