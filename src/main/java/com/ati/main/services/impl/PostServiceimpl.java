package com.ati.main.services.impl;

import com.ati.main.exceptions.ResourceNotFound;
import com.ati.main.model.Category;
import com.ati.main.model.Post;
import com.ati.main.model.User;
import com.ati.main.payload.PostDto;
import com.ati.main.payload.PostResponce;
import com.ati.main.repositories.CategoryRepo;
import com.ati.main.repositories.PostRepo;
import com.ati.main.repositories.UserRepo;
import com.ati.main.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceimpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto CretePost(PostDto postDto ,Integer userid ,Integer categoryid ) {
        User user = this.userRepo.findById(userid).orElseThrow( ()-> {
            return new ResourceNotFound("User", "UserId", userid);
        });
        Category category = this.categoryRepo.findById(categoryid).orElseThrow(()-> {
            return new ResourceNotFound("Category", " CategoryID", categoryid);
        });

        Post post = this.dtotopost(postDto);
        post.setImageName("Default.png");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = this.postRepo.save(post);
        return posttodto( savedPost );
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postid) {

        Post post = this.postRepo.findById(postid).orElseThrow( ()-> {
            return new ResourceNotFound("Post", "PostId", postid);
        });
        post.setTitle(postDto.getTitle());
        post.setPostContent(postDto.getPostContent());
        post.setImageName(postDto.getImageName());

        Post updatedpost = this.postRepo.save(post);
        return  posttodto(updatedpost);
    }

    @Override
    public void DeletePost(Integer postid) {
       Post post = this.postRepo.findById(postid).orElseThrow( ()-> {
           return new ResourceNotFound("Post", "PostId", postid);
       });

       this.postRepo.delete(post);
    }

    @Override
    public PostResponce getAllPost( ) {

        List<Post> posts = this.postRepo.findAll(Sort.by("postId").descending());
        List<PostDto> listofPost =  posts.stream().map( (post) -> this.posttodto(post)).collect(Collectors.toList());

        PostResponce postResponce = new PostResponce();
        postResponce.setContent(listofPost);
        return postResponce;
    }

    @Override
    public PostDto getPostbyId(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow( ()-> {
            return new ResourceNotFound("Post", "PostId", id);
        });
        PostDto postDto = this.posttodto(post);
        return postDto ;
    }

    @Override
    public List<PostDto> getPostbyCategory(Integer categoryid) {

           Category category = this.categoryRepo.findById(categoryid).orElseThrow(()-> new ResourceNotFound("Category","category id",categoryid ));
          List<Post> posts = this.postRepo.findByCategory(category);
          List<PostDto> listofPost =  posts.stream().map( (post) -> this.posttodto(post)).collect(Collectors.toList());
          return listofPost;
    }

    @Override
    public List<PostDto> getAllPostbyUser(Integer Userid) {
        User user = this.userRepo.findById(Userid).orElseThrow(()-> new ResourceNotFound("User","User id",Userid )) ;
        List<Post> posts = this.postRepo.findByUser(user);
        List <PostDto> listofPosts =  posts.stream().map( (post) -> this.posttodto(post)).collect(Collectors.toList());
        return listofPosts;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List <PostDto> listofPosts =  posts.stream().map( (post) -> this.posttodto(post)).collect(Collectors.toList());
        return listofPosts;
    }


    //To convert Post object into PostDTO type
    public PostDto posttodto(Post post) {
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }
    //To convert DTO object into Post type
    private Post dtotopost (PostDto postDto){
        Post post = this.modelMapper.map(postDto,Post.class);
        return post;
    }
}
