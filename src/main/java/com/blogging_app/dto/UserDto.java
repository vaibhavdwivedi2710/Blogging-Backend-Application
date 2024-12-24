package com.blogging_app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    @NotEmpty //NotNull + NotEmpty
    @Size(min =3,message = "UserName must be min of 3 characters")
    private String name;

    @Email(message = "Email address  is not Valid!!")
    @Pattern(regexp=".+@.+\\..+", message="Invalid Email !!")
    private String email;

    @NotEmpty
    @Size(min =3,max = 10,message = "Password Must be min of 3 chars and max of 15 chars !!")
    //@Pattern(regexp = )
    private String password;

    @NotEmpty
    private String about;

}