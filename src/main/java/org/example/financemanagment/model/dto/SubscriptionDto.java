package org.example.financemanagment.model.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long id;
    private String name;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
