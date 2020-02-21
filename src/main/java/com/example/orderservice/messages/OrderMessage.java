package com.example.orderservice.messages;

import com.example.orderservice.dto.OrderLineDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OrderMessage {

    private Integer id;

    private String state;

    private BigDecimal amount;

    private List<OrderLineDto> orderLineList;

    private LocalDate approveDate;

    @JsonCreator
    public OrderMessage(@JsonProperty("id") Integer id,
                        @JsonProperty("state") String state,
                        @JsonProperty("amount") BigDecimal amount,
                        @JsonProperty("orderLineList") List<OrderLineDto> orderLineList,
                        @JsonProperty("approveDate") LocalDate approveDate) {
        this.approveDate = approveDate;
        this.id = id;
        this.amount = amount;
        this.orderLineList = orderLineList;
        this.state = state;
    }
}
