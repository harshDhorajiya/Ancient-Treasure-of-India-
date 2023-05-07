package com.ati.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int commentId;
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_user_id")
    private User user;

}
