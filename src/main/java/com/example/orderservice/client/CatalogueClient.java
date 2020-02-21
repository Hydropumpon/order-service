package com.example.orderservice.client;

import com.example.orderservice.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${client.catalogue-service.name}", fallback = CatalogueClientFallback.class)
public interface CatalogueClient {

    @GetMapping("/catalogue/item/{id}")
    ItemDto getItemById(@PathVariable("id") Integer id);
}
