package com.medicare.services.imp;

import com.medicare.entity.Product;
import com.medicare.repo.ProductRepo;
import com.medicare.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImplementation implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public List<Product> listProduct(){
        return productRepo.findAll();
    }





    @Override
    public Product save(Product product){
        return productRepo.save(product);
    }


/*

    Need to implement later
    public Product update(Product product){
        return productRepo.save(product);
    }
*/

    @Override
    public void delete(Long id){
        productRepo.deleteById(id);
    }
}
