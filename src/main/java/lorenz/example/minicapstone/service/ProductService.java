package lorenz.example.minicapstone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lorenz.example.minicapstone.dto.ProductDTO;
import lorenz.example.minicapstone.dto.UserDTO;
import lorenz.example.minicapstone.entity.ProductEntity;
import lorenz.example.minicapstone.entity.UserEntity;
import lorenz.example.minicapstone.exception.UserAlreadyExist;
import lorenz.example.minicapstone.model.ProductRequest;
import lorenz.example.minicapstone.repository.ProductRepository;
import lorenz.example.minicapstone.repository.UserRepository;
import lorenz.example.minicapstone.util.DateTimeUtil;
import lorenz.example.minicapstone.util.S3StorageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository productRepository;
//    private final DateTimeUtil dateTimeUtil;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;
    private final S3StorageUtil s3StorageUtil;
    public List<ProductDTO> getAllProducts(){
        // get all data from database
        List<ProductEntity> allProducts = productRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));

        //initialize DTO
        List<ProductDTO> allProductsDTO = new ArrayList<>();

        allProducts.forEach(product -> {
            allProductsDTO.add(modelMapper.map(product, ProductDTO.class));
        });

        return allProductsDTO;
    }

    public List<ProductDTO> addProduct(ProductRequest newProduct) {

        // Save new product to database
        productRepository.save(ProductEntity
                .builder()
                .productId(UUID.randomUUID().randomUUID())
                .productName(newProduct.getProductName())
                .imageLink(null)
                .price(newProduct.getPrice())
                .ratings(newProduct.getRatings())
                .type(newProduct.getType())
                .filter(newProduct.getFilter())
                .description(newProduct.getDescription())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build());

        return getAllProducts();
    }


    public List<ProductDTO> deleteProduct(UUID productId) {

        //Get product
        ProductEntity product = productRepository.findByProductId(productId);

        //check if product exist
        if(product == null) throw new UserAlreadyExist("Product doesn't exist");

        //delete product
        productRepository.deleteByProductId(productId);

        return getAllProducts();
    }

    public List<ProductDTO> uploadProductImage(UUID productId, MultipartFile file){
        //Initialize product
        ProductEntity product = productRepository.findByProductId(productId);
        if(product == null) throw new IllegalStateException("product does not exist");

        //Check if file validity
        s3StorageUtil.checkFile(file);

        // Grab some meta data
        Map<String, String> metadata = s3StorageUtil.getMetaData(file);

        // Store the image to S3 bucket
        String path = String.format("%s/%s", "062289/product-lorenz", productId);

        String fileName = String.format("%s-%s", "product", file.getOriginalFilename());

        try{
            s3StorageUtil.save(path, fileName, Optional.of(metadata), file.getInputStream());
            productRepository.save(ProductEntity
                    .builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .imageLink(fileName)
                    .price(product.getPrice())
                    .ratings(product.getRatings())
                    .type(product.getType())
                    .filter(product.getFilter())
                    .description(product.getDescription())
                    .createdDate(product.getCreatedDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build());
        } catch(IOException e){
            throw new IllegalStateException(e);
        }

        return getAllProducts();
    }

    public byte[] downloadProductImage(UUID productId){
        //Initialize product
        ProductEntity product = productRepository.findByProductId(productId);
        if(product == null) throw new IllegalStateException("product does not exist");

        String path = String.format("%s/%s", "062289/product-lorenz", productId);

        return s3StorageUtil.download(path, product.getImageLink());

    }

}
