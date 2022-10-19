package lorenz.example.minicapstone.controller;

import lombok.RequiredArgsConstructor;
import lorenz.example.minicapstone.dto.BlogDTO;
import lorenz.example.minicapstone.dto.ProductDTO;
import lorenz.example.minicapstone.model.BlogRequest;
import lorenz.example.minicapstone.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/getAll")
    public List<BlogDTO> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @PutMapping("/add")
    public List<BlogDTO> addProduct(@RequestBody BlogRequest blogRequest) {
        return blogService.addBlog(blogRequest);
    }

    @DeleteMapping("/delete/{blogId}")
    public List<BlogDTO> deleteBlog(@PathVariable UUID blogId){
        return blogService.deleteBlog(blogId);
    }
}
