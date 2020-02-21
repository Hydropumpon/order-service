package com.example.orderservice.client;

import com.example.orderservice.dto.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class CatalogueClientFallback implements CatalogueClient {

    private static final String UNAVAILABLE = "UNAVAILABLE";

    @Override
    public ItemDto getItemById(Integer id) {
        return ItemDto.builder()
                      .id(id)
                      .description(UNAVAILABLE)
                      .name(UNAVAILABLE)
                      .build();
    }
}
