package com.ati.main.services.impl;

import com.ati.main.exceptions.ResourceNotFound;
import com.ati.main.payload.CategoryDto;
import com.ati.main.repositories.CategoryRepo;
import com.ati.main.services.CategoryService;
import com.ati.main.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.dtotocat(categoryDto);
        Category savedcat = this.categoryRepo.save (cat);
        return cattodto(savedcat);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {

        Category cat = this.categoryRepo.findById(id).orElseThrow( ()-> new ResourceNotFound("Category" ,"ID",id));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryContent(categoryDto.getCategoryContent());

        Category updatedcat = this.categoryRepo.save(cat);
        return cattodto(updatedcat);
    }

    @Override
    public void deleteCategory(Integer id) {


        Category cat = this.categoryRepo.findById(id).orElseThrow( ()-> new ResourceNotFound("Category" ,"ID",id));
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategorybyId(Integer id) {

        Category cat = this.categoryRepo.findById(id).orElseThrow( ()-> new ResourceNotFound("Category" ,"ID",id));
        return this.cattodto(cat);
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> cats = this.categoryRepo.findAll();
        List<CategoryDto> allCatdto = cats.stream().map(cat ->cattodto(cat)).collect(Collectors.toList());
        return allCatdto;
    }

    // methods for mapping DTO obj to Category.class obj
    public CategoryDto cattodto(Category cat) {
        CategoryDto catdto = this.modelMapper.map(cat, CategoryDto.class);
        return catdto;
    }

    private Category dtotocat (CategoryDto catdto){

        Category cat = this.modelMapper.map(catdto,Category.class);
         return cat;
    }
}
