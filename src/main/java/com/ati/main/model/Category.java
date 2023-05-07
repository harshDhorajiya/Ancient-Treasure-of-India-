package com.ati.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY )
    private Integer categoryId;
    @Column( length = 100 )
    private String categoryTitle;
    @Column( )
    private String categoryContent;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
