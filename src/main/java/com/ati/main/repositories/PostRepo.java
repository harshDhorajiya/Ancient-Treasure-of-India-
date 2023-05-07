package com.ati.main.repositories;
import com.ati.main.model.Category;
import com.ati.main.model.Post;
import com.ati.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository< Post ,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like concat('%', ?1, '%')")
    List<Post> findByTitleContaining(String title);
}
