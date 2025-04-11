package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.InventoryTitle;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {

    private ModelMapper modelMapper;

    private InventoryRepository inventoryRepository;

    public InventoryController(ModelMapper modelMapper, InventoryRepository inventoryRepository) {
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping(path="/api/getInventoryTitleList")
    public String InventoryTitleList() {

        List<String> categoryList = inventoryRepository.findDistinctCategoryBy();
        System.out.println("CCB");
        System.out.println(categoryList);
        return "" ;

    }
}
