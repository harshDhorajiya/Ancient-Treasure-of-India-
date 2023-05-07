package com.ati.main.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Integer categoryId;
     @NotEmpty
     @NotNull
    private String categoryTitle;
    @NotNull
    @NotEmpty
     private String categoryContent;
}
