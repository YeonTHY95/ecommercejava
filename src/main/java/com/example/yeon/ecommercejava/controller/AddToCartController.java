package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.AddToCartDTO;
import com.example.yeon.ecommercejava.dto.AddToCartGetResponseDTO;
import com.example.yeon.ecommercejava.dto.AddToCartPOSTRequest;
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
    public ResponseEntity<List<AddToCartGetResponseDTO>> getAddToCart(@RequestParam Long id){
        try {
            addToCartLogger.info("Inside View MyCart");
            List<AddToCartGetResponseDTO> ResponseList = new ArrayList<>();
            List<AddToCartEntity> AddToCartEntityList = addToCartService.getAllAddToCart(id) ;
            addToCartLogger.info("AddToCartEntityList is " + AddToCartEntityList);
            for (AddToCartEntity addToCartEntity : AddToCartEntityList) {
                InventoryEntity inventoryEntity = addToCartEntity.getInventory();
                InventoryDTO inventory = modelMapper.map(inventoryEntity, InventoryDTO.class);
                addToCartLogger.info("After Mapping, InventoryDTO is " + inventory);
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
    public ResponseEntity<List<AddToCartDTO>> postAddToCart(@RequestBody AddToCartPOSTRequest addToCartPOSTRequest) {
        try {
            addToCartLogger.info("Inside POST AddtoCart");
//            AddToCartDTO addToCartDTO = new AddToCartDTO();
//            AddToCartEntity addToCartEntity = modelMapper.map(addToCartDTO, AddToCartEntity.class);
            AddToCartEntity addToCartEntity = addToCartService.mapFromAddToCartPOSTRequestToEntity(addToCartPOSTRequest);
            addToCartLogger.info("before addToCartService.createAddToCart");
            addToCartEntity= addToCartService.createAddToCart(addToCartEntity);
            addToCartLogger.info("After addToCartService.createAddToCart, addToCartEntity is " + addToCartEntity);
            Integer totalQuantity = addToCartRepository.totalQuantity(addToCartEntity.getInventory(),addToCartEntity.getUser(), addToCartEntity.getSelectedColor());

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
