package com.ati.main.services.impl;

import com.ati.main.exceptions.ResourceNotFound;
import com.ati.main.model.Comment;
import com.ati.main.model.Post;
import com.ati.main.model.User;
import com.ati.main.payload.CommentDto;
import com.ati.main.repositories.CommentRepo;
import com.ati.main.repositories.PostRepo;
import com.ati.main.repositories.UserRepo;
import com.ati.main.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
   @Autowired
    private CommentRepo commentRepo;
   @Autowired
   private ModelMapper modelMapper;
   @Autowired
   private UserRepo userRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId , Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow( ()-> { return new ResourceNotFound("Post", "PostId", postId);} );
        User user = this.userRepo.findById(userId).orElseThrow( ()-> new ResourceNotFound("USer","Id",userId));
            Comment comment = this.modelMapper.map( commentDto , Comment.class);
            comment.setPost(post);
            comment.setUser(user);
            Comment savedcomment = this.commentRepo.save(comment);
            return this.modelMapper.map(savedcomment,CommentDto.class);
     }

    @Override
    public Void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow( ()-> new ResourceNotFound( "Commnet", "CommentId" , commentId )  );
        this.commentRepo.delete(comment);
        return null;
    }
}
