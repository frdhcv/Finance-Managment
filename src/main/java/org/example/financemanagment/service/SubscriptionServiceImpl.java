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




    @Override
    public SubscriptionEntity saveSubscription(SubscriptionEntity subscription) {
        return subscriptionDao.save(subscription);
    }

    @Override
    public List<SubscriptionEntity> getAllSubscriptions() {
        return subscriptionDao.findAll();
    }

    @Override
    public Optional<SubscriptionEntity> getSubscriptionById(Long id) {
        return subscriptionDao.findById(id);
    }

    @Override
    public void deleteSubscriptionById(Long id) {
        subscriptionDao.deleteById(id);
    }

    @Override
    public SubscriptionEntity updateSubscription(Long id, SubscriptionEntity updatedSubscription) {
        Optional<SubscriptionEntity> existingSubscriptionOptional = subscriptionDao.findById(id);
        if (existingSubscriptionOptional.isPresent()) {
            SubscriptionEntity existingSubscription = existingSubscriptionOptional.get();
            existingSubscription.setName(updatedSubscription.getName());
            existingSubscription.setPrice(updatedSubscription.getPrice());
            existingSubscription.setStartDate(updatedSubscription.getStartDate());
            existingSubscription.setEndDate(updatedSubscription.getEndDate());
            existingSubscription.setActive(updatedSubscription.isActive());
            return subscriptionDao.save(existingSubscription);
        }
        return null; // veya uygun bir hata işleme stratejisi
    }
}
