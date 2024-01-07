package com.medicare.services.imp;

import com.medicare.entity.Cart;
import com.medicare.entity.CartItem;
import com.medicare.entity.Product;
import com.medicare.entity.User;
import com.medicare.repo.CartRepo;
import com.medicare.repo.ProductRepo;
import com.medicare.services.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartImplementation implements CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;


    @Override
    @Transactional
    public Cart addToCart(User user, Long productId, int quantity) throws ChangeSetPersister.NotFoundException {

        // Taken user cart details or created new cart.
        Cart cart=cartRepo.findByUser(user).orElseGet(()-> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        // Finding the product
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ChangeSetPersister.NotFoundException());

  /*
   We can implement this later.
   if(!(product.getStock() < 1)){
            new ChangeSetPersister.NotFoundException();
        }*/

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        }

        return cartRepo.save(cart);


    }


    @Override
    @Transactional
    public Cart removeFromCart(User user, Long productId) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        cart.getItems().remove(itemToRemove);
        return cartRepo.save(cart);
    }

    @Override
    public Cart cartItems(User user){
        return cartRepo.findByUser(user).get();
    }



}
