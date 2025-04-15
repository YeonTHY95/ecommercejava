package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.InventoryCardInfoDTO;
import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.dto.InventoryIdAndTitleDTO;
import com.example.yeon.ecommercejava.dto.UserDTO;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.repository.InventoryCardInfo;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.InventoryTitle;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger inventoryLogger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(ModelMapper modelMapper, InventoryRepository inventoryRepository) {
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping(path="/api/getInventoryCategory")
    public List<String> InventoryCategory() {

        List<String> categoryList = inventoryRepository.findDistinctCategoryBy();
        System.out.println(categoryList);
        return categoryList ;

    }

    @ResponseBody
    @GetMapping(path="/api/getInventoryDetail")
    public ResponseEntity<InventoryDTO> getInventoryDetail(@RequestParam Long id) {
        inventoryLogger.info("Inside getInventoryDetail API");
        Optional<InventoryEntity> inventory =  inventoryRepository.findById(id);

        if ( inventory.isEmpty()) {
            inventoryLogger.info("Inventory Not Found");
            return new ResponseEntity("Inventory Not Found", HttpStatus.NOT_FOUND);
        }
        InventoryEntity inventoryEntity = inventory.orElseThrow(() -> new RuntimeException("Inventory Not Found"));
        InventoryDTO inventoryDTO = modelMapper.map(inventoryEntity, InventoryDTO.class);
        UserDTO sellerDTO = inventoryDTO.getSellerObject();
        String sellerName = inventoryDTO.getSellerObject().getUsername();
        inventoryLogger.info("UserDTO Seller Name is " + sellerDTO.getUsername());
        inventoryDTO.setSeller(sellerName);
        return new ResponseEntity<>(inventoryDTO, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(path = "/api/getHotSalesInventory")
    public ResponseEntity<List<InventoryCardInfoDTO>> HotSalesInventoryInfo () {
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
        inventoryLogger.info("hotSalesInventoryDTO is " + hotSalesInventoryDTOList);
        return new ResponseEntity(hotSalesInventoryDTOList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(path = "/api/searchInventory")
    public ResponseEntity<List<InventoryCardInfoDTO>> SearchInventory(@RequestParam(value="category") Optional<String> category, @RequestParam(value="title") Optional<String> title ){
        String categoryValue = category.orElseGet(() -> "");
        String titleValue = title.orElseGet(()-> "");
        Pageable pageable = PageRequest.of(0,50);

        try {
            if (categoryValue == "" && titleValue == "") {
                List<InventoryCardInfo> allInventoryEntity = inventoryRepository.searchInventoryByAll(pageable);
                List<InventoryCardInfoDTO> allInventoryList =  allInventoryEntity.stream().map( i -> modelMapper.map(i, InventoryCardInfoDTO.class)).collect(Collectors.toList());
                inventoryLogger.info("Search All Inventory List is " + allInventoryList) ;
                return new ResponseEntity<>(allInventoryList, HttpStatus.OK);
            }
            else if (categoryValue != "" && titleValue == "") {
                List<InventoryCardInfo> allInventoryEntityByCategory = inventoryRepository.searchInventoryByCategory(categoryValue);
                List<InventoryCardInfoDTO> allInventoryList =  allInventoryEntityByCategory.stream().map( i -> modelMapper.map(i, InventoryCardInfoDTO.class)).collect(Collectors.toList());
                inventoryLogger.info("Search All Inventory List By Category is " + allInventoryList) ;
                return new ResponseEntity<>(allInventoryList, HttpStatus.OK);
            }
            else if (titleValue != "" && categoryValue == "") {
                List<InventoryCardInfo> allInventoryEntityByTitle = inventoryRepository.searchInventoryByTitle(titleValue);
                List<InventoryCardInfoDTO> allInventoryList =  allInventoryEntityByTitle.stream().map( i -> modelMapper.map(i, InventoryCardInfoDTO.class)).collect(Collectors.toList());
                inventoryLogger.info("Search All Inventory List By Title is " + allInventoryList) ;
                return new ResponseEntity<>(allInventoryList, HttpStatus.OK);
            }
            else {
                List<InventoryCardInfo> allInventoryEntity = inventoryRepository.searchInventoryByCategoryAndTitle(categoryValue, titleValue);
                List<InventoryCardInfoDTO> allInventoryList =  allInventoryEntity.stream().map( i -> modelMapper.map(i, InventoryCardInfoDTO.class)).collect(Collectors.toList());
                inventoryLogger.info("Search All Inventory List By Category and Title is " + allInventoryList) ;
                return new ResponseEntity<>(allInventoryList, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            inventoryLogger.info(String.valueOf(e));
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    @ResponseBody
    @GetMapping(path = "/api/getInventoryTitleList")
    public ResponseEntity<List<InventoryIdAndTitleDTO>> getInventoryTitleList() {
        List<InventoryIdAndTitleDTO> inventoryTitleAndIdList = inventoryRepository.getInventoryWithIdAndTitle().stream().map(i -> modelMapper.map(i, InventoryIdAndTitleDTO.class)).collect(Collectors.toList());
        System.out.println(inventoryTitleAndIdList);
        return new ResponseEntity<List<InventoryIdAndTitleDTO>>(inventoryTitleAndIdList, HttpStatus.OK) ;
    }

}
