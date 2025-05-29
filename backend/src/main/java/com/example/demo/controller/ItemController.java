package com.example.demo.controller;

import com.example.demo.dto.ItemDto;
import com.example.demo.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Adding new item: {}", itemDto);
        ItemDto response = itemService.addItem(itemDto, userDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Getting all items");
        List<ItemDto> response = itemService.getAllItems(userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> editItem(@PathVariable Long id, @RequestBody ItemDto itemDto, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating existing item: {}", itemDto);
        ItemDto response = itemService.editItem(id, itemDto, userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting existing item: {}", id);
        itemService.deleteItem(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}