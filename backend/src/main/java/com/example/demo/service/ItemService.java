package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private Item toItem(ItemDto itemDto, UserDetails userDetails) {
        return Item.builder()
                .type(itemDto.getType())
                .genre(itemDto.getGenre())
                .title(itemDto.getTitle())
                .review(itemDto.getReview())
                .rating(itemDto.getRating())
                .userId(userRepository.findByUsername(userDetails.getUsername()))
                .build();
    }

    private ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .rating(item.getRating())
                .genre(item.getGenre())
                .review(item.getReview())
                .title(item.getTitle())
                .type(item.getType())
                .build();
    }

    public ItemDto addItem(ItemDto itemDto, UserDetails userDetails) {
        Item item = toItem(itemDto, userDetails);
        return toItemDto(itemRepository.save(item));
    }


    public List<ItemDto> getAllItems(UserDetails userDetails) {
        if (itemRepository.findAll().isEmpty()) {
            throw new ItemNotFoundException("No items found");
        }

        return itemRepository.findAll().stream().map(item -> toItemDto(item)).collect(Collectors.toList());
    }


    public ItemDto editItem(Long id, ItemDto itemDto, UserDetails userDetails) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setTitle(itemDto.getTitle());
            item.setGenre(itemDto.getGenre());
            item.setRating(itemDto.getRating());
            item.setReview(itemDto.getReview());
            item.setType(itemDto.getType());
            item.setUserId(userRepository.findByUsername(userDetails.getUsername()));
        }

        else {
            throw new ItemNotFoundException("Item with id " + id + "not found");
        }

        return toItemDto(itemRepository.save(item));
    }

    public void deleteItem(Long id, UserDetails userDetails) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            itemRepository.delete(item);
        }
        else {
            throw new ItemNotFoundException("Item with id " + id + "not found");
        }
    }
}
