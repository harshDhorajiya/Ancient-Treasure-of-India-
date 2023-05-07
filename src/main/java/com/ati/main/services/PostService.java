package com.ati.main.services;

import com.ati.main.model.Post;
import com.ati.main.payload.PostDto;
import com.ati.main.payload.PostResponce;

import java.util.List;

public interface PostService {

    PostDto CretePost(PostDto postDto , Integer userid ,Integer categoryid);
    PostDto updatePost(PostDto postDto , Integer Postid);
    void DeletePost(Integer id);
    PostResponce getAllPost( );
    PostDto getPostbyId(Integer id);
    List<PostDto> getPostbyCategory(Integer Categoryid);
    List<PostDto> getAllPostbyUser (Integer Userid);
    List<PostDto> searchPost(String keyword);

}
