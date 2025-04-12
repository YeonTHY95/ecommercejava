package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.dto.UserDTO;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.InventoryTitle;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@RestController
public class InventoryController {

    private ModelMapper modelMapper;

    private InventoryRepository inventoryRepository;

    public InventoryController(ModelMapper modelMapper, InventoryRepository inventoryRepository) {
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping(path="/api/getInventoryTitleList")
    public List<String> InventoryTitleList() {

        List<String> categoryList = inventoryRepository.findDistinctCategoryBy();
        System.out.println("CCB");
        System.out.println(categoryList);
        return categoryList ;

    }

    @ResponseBody
    @GetMapping(path="/api/getInventoryDetail/{id}")
    public ResponseEntity<InventoryDTO> getInventoryDetail(@PathVariable Long id) {
        Optional<InventoryEntity> inventory =  inventoryRepository.findById(id);

        if ( inventory.isEmpty()) {
            return new ResponseEntity("Inventory Not Found", HttpStatus.NOT_FOUND);
        }
        InventoryEntity inventoryEntity = inventory.orElseThrow(() -> new RuntimeException("Inventory Not Found"));
        InventoryDTO inventoryDTO = modelMapper.map(inventoryEntity, InventoryDTO.class);
        UserDTO sellerDTO = inventoryDTO.getSellerObject();
        String sellerName = inventoryDTO.getSellerObject().getUsername();
        System.out.println("UserDTO Seller Name is " + sellerDTO.getUsername());
        inventoryDTO.setSeller(sellerName);
        return new ResponseEntity<>(inventoryDTO, HttpStatus.ACCEPTED);


    }
}
