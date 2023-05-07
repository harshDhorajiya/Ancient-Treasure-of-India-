package com.ati.main.payload;

import com.ati.main.model.Category;
import com.ati.main.model.Comment;
import com.ati.main.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

    private Integer postid;
    private String title;
    private String postContent;
    private String imageName;
    private Date postDate;
    private CategoryDto category;
    private UserDto user;

    private List<Comment> comments = new ArrayList<>();
}
