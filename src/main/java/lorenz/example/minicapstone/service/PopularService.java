package lorenz.example.minicapstone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lorenz.example.minicapstone.dto.BlogDTO;
import lorenz.example.minicapstone.dto.PopularDTO;
import lorenz.example.minicapstone.entity.BlogEntity;
import lorenz.example.minicapstone.entity.PopularEntity;
import lorenz.example.minicapstone.entity.ProductEntity;
import lorenz.example.minicapstone.exception.UserAlreadyExist;
import lorenz.example.minicapstone.model.BlogRequest;
import lorenz.example.minicapstone.model.PopularRequest;
import lorenz.example.minicapstone.repository.PopularRepository;
import lorenz.example.minicapstone.util.DateTimeUtil;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PopularService {

    private final PopularRepository popularRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    public List<PopularDTO> getAllPopularProducts(){
        // get all data from database
        List<PopularEntity> allPopularProducts = popularRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));
        //initialize DTO
        List<PopularDTO> allPopularProductDTO = new ArrayList<>();

        allPopularProducts.forEach(popular -> {
            allPopularProductDTO.add(modelMapper.map(popular, PopularDTO.class));
        });
        return allPopularProductDTO;
    }

    public List<PopularDTO> addPopularProduct(PopularRequest newPopularProduct) {
        // Save new blog to database
        popularRepository.save(PopularEntity
                .builder()
                .productId(UUID.randomUUID().randomUUID())
                .productName(newPopularProduct.getProductName())
                .imageLink(null)
                .price(newPopularProduct.getPrice())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build());
        return getAllPopularProducts();
    }

    public List<PopularDTO> deletePopularProduct(UUID productId) {

        //Get product
        PopularEntity blog = popularRepository.findByProductId(productId);

        //check if product exist
        if(blog == null) throw new UserAlreadyExist("product doesn't exist");

        //delete product
        popularRepository.deleteByProductId(productId);

        return getAllPopularProducts();
    }

}
