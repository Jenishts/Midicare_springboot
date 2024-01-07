package com.medicare.services;

import com.medicare.entity.Cart;
import com.medicare.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface CartService {

    Cart addToCart(User user, Long productId, int quantity) throws ChangeSetPersister.NotFoundException;

    Cart removeFromCart(User user, Long productId) throws ChangeSetPersister.NotFoundException;

    Cart cartItems(User user);
}
