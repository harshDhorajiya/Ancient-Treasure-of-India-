package com.ati.main.controllers;

import com.ati.main.payload.CategoryDto;
import com.ati.main.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private CategoryService categoryService;

    //Create category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCat ( @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.CREATED);
    }

    //put update category
     @PutMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> updateCat (@Valid @RequestBody CategoryDto categoryDto , @PathVariable Integer categoryid) {
        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryid);
        return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK);
    }

    //Delete category
    @DeleteMapping("/{categoryid}")
    public ResponseEntity<?> deleteCat ( @PathVariable Integer categoryid) {
        this.categoryService.deleteCategory(categoryid);
        return new ResponseEntity(Map.of("Message","Category deleted successfully"),HttpStatus.OK );
    }

    //Get category
    @GetMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> grtCatbyid ( @PathVariable Integer categoryid) {
        CategoryDto CategoryDto = this.categoryService.getCategorybyId(categoryid);
        return ResponseEntity.ok(CategoryDto);
    }

    //Get all Category
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getallCat () {
     return ResponseEntity.ok(this.categoryService.getAllCategory());
    }

}


