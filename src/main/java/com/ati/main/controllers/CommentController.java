package com.ati.main.controllers;

import com.ati.main.payload.CommentDto;
import com.ati.main.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/post/{postId}/{userId}/user")
    public ResponseEntity <CommentDto> createComment(@RequestBody CommentDto commentDto ,
                                                     @PathVariable Integer postId , @PathVariable Integer userId){
        CommentDto newcomment = this.commentService.createComment(commentDto , postId ,userId);
        return new ResponseEntity<CommentDto>(newcomment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment (@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
     return new  ResponseEntity(Map.of("message" ,"Comment deleted successfully") , HttpStatus.OK);
    }




}
