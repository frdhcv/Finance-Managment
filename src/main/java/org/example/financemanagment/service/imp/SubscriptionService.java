package org.example.financemanagment.service.imp;

import org.example.financemanagment.domain.entity.SubscriptionEntity;
import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    SubscriptionEntity saveSubscription(SubscriptionEntity subscription);
    List<SubscriptionEntity> getAllSubscriptions();
    Optional<SubscriptionEntity> getSubscriptionById(Long id);
    void deleteSubscriptionById(Long id);
    SubscriptionEntity updateSubscription(Long id, SubscriptionEntity updatedSubscription);
}
