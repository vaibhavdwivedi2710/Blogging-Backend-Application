package com.blogging_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {


    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Min size of category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10,message = "Min size of category Description is 10")
    private String categoryDescription;

}
