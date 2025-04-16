package com.example.yeon.ecommercejava.services;

import com.example.yeon.ecommercejava.dto.AddToCartPOSTRequest;
import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.repository.AddToCartRepository;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddToCartService {

    public static final Logger addToCartLogger = LoggerFactory.getLogger(AddToCartService.class);

    private AddToCartRepository addToCartRepository;
    private UserRepository userRepository;
    private InventoryRepository inventoryRepository;

    public AddToCartService(AddToCartRepository addToCartRepository, UserRepository userRepository,InventoryRepository inventoryRepository) {
        this.addToCartRepository= addToCartRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public AddToCartEntity createAddToCart(AddToCartEntity addToCartEntity) {
        return addToCartRepository.save(addToCartEntity);
    }

    public List<AddToCartEntity> getAllAddToCart(Long userId) {
        Optional<UserEntity> Buyer = userRepository.findById(userId);
        UserEntity SpecificBuyer = Buyer.orElseThrow();
        return addToCartRepository.getAllInventoryBySpecificUser(SpecificBuyer);
    }

    public List<AddToCartEntity> getDuplicateAddToCart(AddToCartEntity addToCartEntity) {
        // Long inventoryId, Long userId, String selectedColor
        return addToCartRepository.getDuplicateAddToCart(addToCartEntity.getInventory(),addToCartEntity.getUser(),addToCartEntity.getSelectedColor());
    }

    public AddToCartEntity mapFromAddToCartPOSTRequestToEntity(AddToCartPOSTRequest requestDTO) {
        try{
            addToCartLogger.info("Inside mapFromAddToCartPOSTRequestToEntity");
            UserEntity userEntity = userRepository.findById(requestDTO.getUser()).orElseThrow();
            InventoryEntity inventoryEntity = inventoryRepository.findById(requestDTO.getInventory()).orElseThrow();
            addToCartLogger.info("userEntity is " + userEntity);
            addToCartLogger.info("inventoryEntity is " + inventoryEntity);
            AddToCartEntity addToCartEntity = new AddToCartEntity();
            addToCartEntity.setQuantity(requestDTO.getQuantity());
            addToCartEntity.setSelectedColor(requestDTO.getSelectedColor());
            addToCartEntity.setInventory(inventoryEntity);
            addToCartEntity.setUser(userEntity);
            addToCartLogger.info("addToCartEntity is " + addToCartEntity);
            return addToCartEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllAddToCartByBuyerId(Long buyerId) {
        try {
            UserEntity buyerEntity = userRepository.findById(buyerId).orElseThrow();
            addToCartRepository.deleteAllAddToCartByBuyerId(buyerEntity);
            addToCartLogger.info("Successfully Delete AddToCart for Buyer " + buyerEntity);
        } catch (Exception e) {
            addToCartLogger.info(String.valueOf(e));
            throw new RuntimeException(e);
        }

    }
}
