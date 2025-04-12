package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.InventoryCardInfoDTO;
import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.dto.UserDTO;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.repository.InventoryCardInfo;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.InventoryTitle;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @ResponseBody
    @GetMapping(path = "/api/getHotSalesInventory")
    public ResponseEntity HotSalesInventoryInfo () {
        Pageable p = PageRequest.of(0, 5);
        // Using Pageable because JPQL does not support LIMIT
//      List<InventoryCardInfo> hotSalesInventories = inventoryRepository.getHotSalesScoreInventory(p);
        List<InventoryCardInfo> hotSalesInventories = inventoryRepository.getHotSalesScoreInventoryNativeSQL();
        System.out.println("hotSalesInventories is " + hotSalesInventories);
//        InventoryDTO hotSalesInventoryDTO = modelMapper.map(hotSalesInventories, InventoryDTO.class);
        List<InventoryCardInfoDTO> hotSalesInventoryDTOList = hotSalesInventories.stream().map(h -> modelMapper.map(h, InventoryCardInfoDTO.class)).collect(Collectors.toList());
//        List<InventoryDTO> hotSalesInventoryDTOList = hotSalesInventories.stream()
//                .map(item -> {
//                    InventoryDTO dto = new InventoryDTO();
//                    dto.setId(item.getId());
//                    dto.setTitle(item.getTitle());
//                    dto.setName(item.getName());
//                    dto.setPrice(item.getPrice());
//                    dto.setRating(item.getRating());
//                    dto.setCategory(item.getCategory());
//                    dto.setColor(item.getColor());
//                    dto.setImageUrl(item.getImageUrl());
//                    return dto;
//                })
//                .collect(Collectors.toList());
        System.out.println("hotSalesInventoryDTO is " + hotSalesInventoryDTOList);
        return new ResponseEntity(hotSalesInventoryDTOList, HttpStatus.ACCEPTED);
    }
}
