package com.example.yeon.ecommercejava.services;

import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.repository.AddToCartRepository;
import com.example.yeon.ecommercejava.repository.UserRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddToCartService {
    private AddToCartRepository addToCartRepository;
    private UserRepository userRepository;

    public AddToCartService(AddToCartRepository addToCartRepository, UserRepository userRepository) {
        this.addToCartRepository= addToCartRepository;
        this.userRepository = userRepository;
    }

    public AddToCartEntity createAddToCart(AddToCartEntity addToCartEntity) {
        return addToCartRepository.save(addToCartEntity);
    }

    public List<AddToCartEntity> getAllAddToCart(Long userId) {
        Optional<UserEntity> Buyer = userRepository.findById(userId);
        UserEntity SpecificBuyer = Buyer.orElseThrow();
        return addToCartRepository.getAllInventoryBySpecificUser(SpecificBuyer.getId());
    }

    public List<AddToCartEntity> getDuplicateAddToCart(AddToCartEntity addToCartEntity) {
        // Long inventoryId, Long userId, String selectedColor
        return addToCartRepository.getDuplicateAddToCart(addToCartEntity.getInventory().getId(),addToCartEntity.getUser().getId(),addToCartEntity.getSelectedColor());
    }
}
