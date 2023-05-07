package com.ati.main.controllers;

import com.ati.main.payload.PostDto;
import com.ati.main.payload.PostResponce;
import com.ati.main.services.FileService;
import com.ati.main.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")

public class postcontroller {
    @Autowired
    private PostService postService;
    private PostDto postDto;
    @Autowired
    private FileService fileService;
     @Value("${project.image}")
    private String path;
    //create post
   @PostMapping("/{userid}/{categoryid}/post")
   public ResponseEntity<PostDto> createPost ( @RequestBody PostDto postDto, @PathVariable Integer userid,
                                              @PathVariable Integer categoryid) {
       this.postDto = postDto;
       PostDto createpost = this.postService.CretePost(postDto , userid ,categoryid);
        return new ResponseEntity<PostDto>(createpost, HttpStatus.CREATED);

   }

   //get by user
    @GetMapping("user/{userid}/post")
    public ResponseEntity <java.util.List<PostDto>> getPostbyUser(@PathVariable Integer userid){
        java.util.List<PostDto> posts = this.postService.getAllPostbyUser(userid);
         return new ResponseEntity<java.util.List<PostDto>>(posts,HttpStatus.OK);
    }

    //get by category
    @GetMapping("category/{categoryid}/post")
    public  ResponseEntity <java.util.List<PostDto>> getbyCategory (@PathVariable Integer categoryid){
        java.util.List<PostDto> posts = this.postService.getPostbyCategory(categoryid);
        return new ResponseEntity<java.util.List<PostDto>>(posts,HttpStatus.OK);
    }

    //update post
    @PutMapping("/post/{postid}")
    public ResponseEntity<PostDto> updatePost ( @RequestBody PostDto postDto, @PathVariable Integer postid){
      PostDto updatedPost = this.postService.updatePost(postDto,postid);
      return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/post/{postid}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postid){
       this.postService.DeletePost(postid);
       return new ResponseEntity(Map.of("message", "Post deleted successfully ") ,HttpStatus.OK);
    }


    //get post by id
    @GetMapping("/post/{postid}")
    public ResponseEntity<PostDto> getPostbyid (@PathVariable Integer postid){
     PostDto postDto = this.postService.getPostbyId(postid);
     return new ResponseEntity(postDto,HttpStatus.OK );
    }

    //get all post
    @GetMapping("/post")
    public ResponseEntity<PostResponce> getAllPost (){
       PostResponce allPost =   this.postService.getAllPost();
      return new ResponseEntity<PostResponce>(allPost,HttpStatus.OK) ;
   }

    //search post
    @GetMapping("/post/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable  String keywords){
      List<PostDto> posts = this.postService.searchPost(keywords);
      return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile image , @PathVariable Integer postId) throws IOException {

        PostDto postDto =  this.postService.getPostbyId(postId);
       String filename = this.fileService.uploadImage(path,image);
        postDto.setImageName(filename);
         PostDto updatedpostDto = this.postService.updatePost(postDto,postId);
       return new ResponseEntity<PostDto>(updatedpostDto,HttpStatus.OK);
   }

   //to serve image
    @GetMapping(value = "/post/serve/{imgname}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void showImage(@PathVariable("imgname") String imgname , HttpServletResponse response) throws IOException {
        InputStream img = this.fileService.getResource(path,imgname);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(img,response.getOutputStream());
    }

}
