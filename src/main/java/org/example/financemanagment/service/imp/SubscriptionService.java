package org.example.financemanagment.service.imp;

import org.example.financemanagment.domain.entity.SubscriptionEntity;

import java.util.List;

public interface SubscriptionService {
    SubscriptionEntity save(SubscriptionEntity subscriptionEntity);

    List< SubscriptionEntity> findAll();

    SubscriptionEntity findById(Long id) ;

    void deleteById(Long id) ;

    SubscriptionEntity update(Long id, SubscriptionEntity studentDto);

    SubscriptionEntity partialUpdate(Long id, SubscriptionEntity studentDto);
 }