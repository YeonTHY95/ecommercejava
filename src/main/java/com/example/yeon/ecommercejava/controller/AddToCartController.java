package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.AddToCartDTO;
import com.example.yeon.ecommercejava.dto.AddToCartGetResponseDTO;
import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.repository.AddToCartRepository;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.services.AddToCartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AddToCartController {

    public static final Logger addToCartLogger = LoggerFactory.getLogger(AddToCartController.class);

    private ModelMapper modelMapper ;
    private AddToCartRepository addToCartRepository ;
    private AddToCartService addToCartService;
    public AddToCartController(ModelMapper modelMapper, AddToCartRepository addToCartRepository, AddToCartService addToCartService){
        this.modelMapper = modelMapper;
        this.addToCartRepository = addToCartRepository;
        this.addToCartService = addToCartService;
    }

    @ResponseBody
    @GetMapping(path = "/api/myCart")
    public ResponseEntity<List<AddToCartGetResponseDTO>> getAddToCart(@RequestParam Long userId){
        try {
            List<AddToCartGetResponseDTO> ResponseList = new ArrayList<>();
            List<AddToCartEntity> AddToCartEntityList = addToCartService.getAllAddToCart(userId) ;
            for (AddToCartEntity addToCartEntity : AddToCartEntityList) {
                InventoryEntity inventoryEntity = addToCartEntity.getInventory();
                InventoryDTO inventory = modelMapper.map(inventoryEntity, InventoryDTO.class);
                ResponseList.add(new AddToCartGetResponseDTO(inventory,addToCartEntity.getQuantity(),addToCartEntity.getSelectedColor()));
            }
            return new ResponseEntity<>(ResponseList, HttpStatus.OK);
        }
        catch (Exception e) {
            addToCartLogger.info(String.valueOf(e));
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(path="api/addToCart")
    public ResponseEntity<List<AddToCartDTO>> postAddToCart(@RequestBody AddToCartDTO addToCartDTO) {
        try {
            AddToCartEntity addToCartEntity = modelMapper.map(addToCartDTO, AddToCartEntity.class);
            addToCartService.createAddToCart(addToCartEntity);

            Integer totalQuantity = addToCartRepository.totalQuantity(addToCartEntity.getInventory().getId(),addToCartEntity.getUser().getId(), addToCartEntity.getSelectedColor());

            List<AddToCartEntity> duplicatedAddToCartList = addToCartService.getDuplicateAddToCart(addToCartEntity);

            if(duplicatedAddToCartList.size() == 0 ) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else if(duplicatedAddToCartList.size()>1) {
                // Get First duplicate to update to latest Quantity
                AddToCartEntity firstDuplicateAddToCart = duplicatedAddToCartList.get(0);
                firstDuplicateAddToCart.setQuantity(totalQuantity);
                addToCartRepository.save(firstDuplicateAddToCart);

                // Delete other duplicates
                List<AddToCartEntity> theRestDuplicateAddToCartList = duplicatedAddToCartList.subList(1,duplicatedAddToCartList.size());
                addToCartRepository.deleteAll(theRestDuplicateAddToCartList);
            }
            List<AddToCartEntity> addToCartUpdatedEntityList = addToCartService.getAllAddToCart(addToCartEntity.getUser().getId());
            List<AddToCartDTO> addToCartDTOList = addToCartUpdatedEntityList.stream().map(a -> modelMapper.map(a, AddToCartDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(addToCartDTOList ,HttpStatus.CREATED) ;
        }
        catch (Exception e) {
            addToCartLogger.info(String.valueOf(e));
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
