package com.medicare.controller;

import com.medicare.entity.Cart;
import com.medicare.entity.Product;
import com.medicare.entity.User;
import com.medicare.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> showCart(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(cartService.cartItems(user));
    }

    @PostMapping("/{id}/{stock}")
    public ResponseEntity<String> addtoCart(@AuthenticationPrincipal User user, @PathVariable("id") Long id,@PathVariable("stock") int quantity) throws ChangeSetPersister.NotFoundException {
        cartService.addToCart(user, id, quantity);
        return ResponseEntity.ok("Added to Cart");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@AuthenticationPrincipal User user, @PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {
        cartService.removeFromCart(user, id);
        return ResponseEntity.ok("Removed from cart");
    }

}
