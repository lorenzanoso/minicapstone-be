package lorenz.example.minicapstone.controller;

import lombok.RequiredArgsConstructor;
import lorenz.example.minicapstone.dto.ProductDTO;
import lorenz.example.minicapstone.dto.UserDTO;
import lorenz.example.minicapstone.model.ProductRequest;
import lorenz.example.minicapstone.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/add")
    public List<ProductDTO> addProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }
    @DeleteMapping("/delete/{productId}")
    public List<ProductDTO> deleteProduct(@PathVariable UUID productId){
        return productService.deleteProduct(productId);
    }

    @PutMapping(path = "/{productId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> uploadProductImage(@PathVariable UUID productId, @RequestParam("file")MultipartFile file){
        return productService.uploadProductImage(productId, file);
    }

    @GetMapping(path = "{productId}/download")
    public byte[] downloadProductImage(@PathVariable("productId") UUID userProfileId){
        return productService.downloadProductImage(userProfileId);
    }

}
