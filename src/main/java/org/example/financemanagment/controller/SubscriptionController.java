package org.example.financemanagment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.financemanagment.domain.dao.SubscriptionDAO;
import org.example.financemanagment.domain.entity.SubscriptionEntity;
import org.example.financemanagment.service.imp.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionServiceService;
    private final SubscriptionDAO subscriptionDAO;

    @GetMapping("/total-price")
    public ResponseEntity<Double> getTotalPrice() {
        double totalPrice = subscriptionDAO.getTotalPriceOfSubscriptions();
        return ResponseEntity.ok(totalPrice);
    }
    @GetMapping("/{id}")
    public SubscriptionEntity getById(@PathVariable Long id)  {
        log.info("Get subscription by id:\"{}\"", id);
        return subscriptionServiceService.findById(id);
    }

    @PostMapping
    public SubscriptionEntity createSubscription(@RequestBody SubscriptionEntity subscriptionEntity) {
        log.info("Create subscription:\"{}\"", subscriptionEntity );
        return subscriptionServiceService.save(subscriptionEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id)  {
        log.info("Delete subscription by id:\"{}\"", id);
        subscriptionServiceService.deleteById(id);
    }

    @PutMapping("/{id}")
    public SubscriptionEntity updateSubscription(@PathVariable Long id, @RequestBody SubscriptionEntity subscriptionEntity)  {
        log.info("Update subscription with id:\"{}\"", id);
        return subscriptionServiceService.update(id,subscriptionEntity);
    }

    @PatchMapping("/{id}")
    public SubscriptionEntity partialUpdateSubscription(@PathVariable Long id, @RequestBody SubscriptionEntity subscriptionEntity)  {
        log.info("Partially update subscription with id:\"{}\"", id);
        return subscriptionServiceService.partialUpdate(id, subscriptionEntity);
    }

}