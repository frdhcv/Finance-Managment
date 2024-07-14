//package org.example.financemanagment.controller;
//
//import org.example.financemanagment.domain.entity.SubscriptionEntity;
//import org.example.financemanagment.service.SubscriptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/subscriptions")
//public class SubscriptionController {
//
//    private final SubscriptionService subscriptionService;
//
//    @Autowired
//    public SubscriptionController(SubscriptionService subscriptionService) {
//        this.subscriptionService = subscriptionService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SubscriptionEntity>> getAllSubscriptions() {
//        List<SubscriptionEntity> subscriptions = subscriptionService.getAllSubscriptions();
//        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SubscriptionEntity> getSubscriptionById(@PathVariable Long id) {
//        Optional<SubscriptionEntity> subscriptionOptional = subscriptionService.getSubscriptionById(id);
//        return subscriptionOptional.map(subscription -> new ResponseEntity<>(subscription, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping
//    public ResponseEntity<SubscriptionEntity> createSubscription(@RequestBody SubscriptionEntity subscription) {
//        SubscriptionEntity savedSubscription = subscriptionService.saveSubscription(subscription);
//        return new ResponseEntity<>(savedSubscription, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<SubscriptionEntity> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionEntity updatedSubscription) {
//        SubscriptionEntity updated = subscriptionService.updateSubscription(id, updatedSubscription);
//        if (updated != null) {
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
//        subscriptionService.deleteSubscriptionById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
