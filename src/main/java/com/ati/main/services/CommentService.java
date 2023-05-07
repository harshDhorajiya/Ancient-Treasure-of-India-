package com.ati.main.services;

import com.ati.main.payload.CommentDto;

public interface CommentService {

    CommentDto createComment (CommentDto commentDto , Integer PostId , Integer userId);
    Void deleteComment(Integer commentId);
}
