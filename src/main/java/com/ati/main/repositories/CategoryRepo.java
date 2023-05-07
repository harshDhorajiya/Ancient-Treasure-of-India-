package com.ati.main.repositories;

import com.ati.main.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository <Category ,Integer> {

}
