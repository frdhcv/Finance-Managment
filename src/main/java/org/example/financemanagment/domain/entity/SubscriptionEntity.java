package org.example.financemanagment.domain.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {
    private Long id;
    private String name;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}