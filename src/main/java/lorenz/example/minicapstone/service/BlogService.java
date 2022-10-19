package lorenz.example.minicapstone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lorenz.example.minicapstone.dto.BlogDTO;
import lorenz.example.minicapstone.entity.BlogEntity;
import lorenz.example.minicapstone.exception.UserAlreadyExist;
import lorenz.example.minicapstone.model.BlogRequest;
import lorenz.example.minicapstone.repository.BlogRepository;
import lorenz.example.minicapstone.util.DateTimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    public List<BlogDTO> getAllBlogs(){
        // get all data from database
        List<BlogEntity> allBlogs = blogRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));
        //initialize DTO
        List<BlogDTO> allBlogsDTO = new ArrayList<>();

        allBlogs.forEach(blog -> {
            allBlogsDTO.add(modelMapper.map(blog, BlogDTO.class));
        });
        return allBlogsDTO;
    }

    public List<BlogDTO> addBlog(BlogRequest newBlog) {
        // Save new blog to database
        blogRepository.save(BlogEntity
                .builder()
                .blogId(UUID.randomUUID().randomUUID())
                .blogName(newBlog.getBlogName())
                .blogAuthor(newBlog.getBlogAuthor())
                .imageLink(null)
                .description(newBlog.getDescription())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build());
        return getAllBlogs();
    }

    public List<BlogDTO> deleteBlog(UUID blogId) {

        //Get product
        BlogEntity blog = blogRepository.findByBlogId(blogId);

        //check if product exist
        if(blog == null) throw new UserAlreadyExist("Blog doesn't exist");

        //delete product
        blogRepository.deleteByBlogId(blogId);

        return getAllBlogs();
    }



}
