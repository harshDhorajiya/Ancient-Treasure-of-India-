package com.ati.main.payload;

import com.ati.main.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends User{

    private int commentId;
    private String comment;
    private User user;
}
