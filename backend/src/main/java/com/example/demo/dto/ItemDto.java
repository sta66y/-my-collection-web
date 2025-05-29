package com.example.demo.dto;

import com.example.demo.model.Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private Type type;
    private String title;
    private String genre;
    private Integer rating;
    private String review;
}
