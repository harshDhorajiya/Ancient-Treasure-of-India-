package com.ati.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "Post_Title" )
    private String title;
    @Column(length = 5000)
    private String postContent;
    private String imageName;
    private Date postDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
