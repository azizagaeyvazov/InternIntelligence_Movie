package com.intern_intelligence.movie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "name is required")
    private String fullName;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = ".*\\.(com|org|net|edu|ru)$", message = "Email must end with .com, .org, .net, .edu or .ru")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8,max = 40, message = "Password must be at least 8, max 40 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;
}
