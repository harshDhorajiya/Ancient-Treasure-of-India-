package com.ati.main.services;

import com.ati.main.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    //Create
     CategoryDto createCategory (CategoryDto categoryDto );

    // update
     CategoryDto updateCategory (CategoryDto categoryDto, Integer categoryid);

    // Delete
    void deleteCategory ( Integer id);

    //Get
    CategoryDto getCategorybyId (Integer id);

    //Get all
     List<CategoryDto> getAllCategory ();

}
