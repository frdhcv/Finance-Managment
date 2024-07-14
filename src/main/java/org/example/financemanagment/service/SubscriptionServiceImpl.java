package org.example.financemanagment.service;

import org.example.financemanagment.domain.dao.Dao;
import org.example.financemanagment.domain.entity.SubscriptionEntity;
import org.example.financemanagment.service.imp.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final Dao<SubscriptionEntity, Long> subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(Dao<SubscriptionEntity, Long> subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionEntity save(SubscriptionEntity subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<SubscriptionEntity> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public SubscriptionEntity findById(Long id) {
        Optional<SubscriptionEntity> subscription = subscriptionRepository.findById(id);
        return subscription.orElse(null);  // Return null if not found, or handle it differently if needed
    }

    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public SubscriptionEntity update(Long id, SubscriptionEntity updatedSubscription) {
        Optional<SubscriptionEntity> existingSubscriptionOpt = subscriptionRepository.findById(id);
        if (existingSubscriptionOpt.isPresent()) {
            updatedSubscription.setId(id);
            return subscriptionRepository.save(updatedSubscription);
        }
        return null;  // Or throw an exception if you prefer
    }

    @Override
    public SubscriptionEntity partialUpdate(Long id, SubscriptionEntity subscriptionDto) {
        Optional<SubscriptionEntity> existingSubscriptionOpt = subscriptionRepository.findById(id);
        if (existingSubscriptionOpt.isPresent()) {
            SubscriptionEntity existingSubscription = existingSubscriptionOpt.get();

            // Perform partial updates (only update non-null fields)
            if (subscriptionDto.getName() != null) {
                existingSubscription.setName(subscriptionDto.getName());
            }
            if (subscriptionDto.getId() != null) {
                existingSubscription.setPrice(subscriptionDto.getPrice());
            }


            return subscriptionRepository.save(existingSubscription);
        }
        return null;  // Or throw an exception if you prefer
    }
}