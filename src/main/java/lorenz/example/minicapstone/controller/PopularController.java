package lorenz.example.minicapstone.controller;

import lombok.RequiredArgsConstructor;
import lorenz.example.minicapstone.dto.BlogDTO;
import lorenz.example.minicapstone.dto.PopularDTO;
import lorenz.example.minicapstone.model.BlogRequest;
import lorenz.example.minicapstone.model.PopularRequest;
import lorenz.example.minicapstone.service.BlogService;
import lorenz.example.minicapstone.service.PopularService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/popular")
@RequiredArgsConstructor
public class PopularController {

    private final PopularService popularService;

    @GetMapping("/getAll")
    public List<PopularDTO> getAllPopularProducts(){
        return popularService.getAllPopularProducts();
    }

    @PutMapping("/add")
    public List<PopularDTO> addProduct(@RequestBody PopularRequest popularRequest) {
        return popularService.addPopularProduct(popularRequest);
    }

    @DeleteMapping("/delete/{productId}")
    public List<PopularDTO> deletePopularProduct(@PathVariable UUID productId){
        return popularService.deletePopularProduct(productId);
    }

}
