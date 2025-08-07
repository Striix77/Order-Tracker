package edu.bbte.idde.meim2276.backend.controllers;


import edu.bbte.idde.meim2276.backend.config.VapidProperties;
import edu.bbte.idde.meim2276.backend.controllers.dto.NotificationRequest;
import edu.bbte.idde.meim2276.backend.controllers.dto.SubscriptionRequest;
import edu.bbte.idde.meim2276.backend.dao.datatypes.Subscription;
import edu.bbte.idde.meim2276.backend.dao.repositories.SubscriptionRepository;

import edu.bbte.idde.meim2276.backend.dao.repositories.UserDbRepository;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final transient PushService pushService;


    private final SubscriptionRepository subscriptionRepository;
    private final UserDbRepository userDbRepository;

    @Autowired
    public NotificationController(VapidProperties vapidProperties, SubscriptionRepository subscriptionRepository, UserDbRepository userDbRepository) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        log.info("Vapid properties: {}", vapidProperties.getPrivateKey());
        this.pushService = new PushService()
                .setPublicKey(vapidProperties.getPublicKey())
                .setPrivateKey(vapidProperties.getPrivateKey());
        this.subscriptionRepository = subscriptionRepository;
        this.userDbRepository = userDbRepository;
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody SubscriptionRequest request) {
        log.info("Received subscription request: {}", request);
        Subscription subscription = new Subscription(request.getEndpoint(), request.getKeys().p256dh, request.getKeys().auth, request.getUsername());
        subscriptionRepository.save(subscription);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody SubscriptionRequest request) {
        log.info("Received unsubscription request: {}", request);
        Subscription subscription = new Subscription(request.getEndpoint(), request.getKeys().p256dh, request.getKeys().auth, request.getUsername());
        subscriptionRepository.deleteByAuth(subscription.getAuth());
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        log.info("Received notification request: {}", request);
        try {
            byte[] payload = String.format("{\"title\":\"%s\", \"body\":\"%s\", \"icon\":\"%s\", \"url\":\"%s\"}",
                    request.getTitle(), request.getBody(), request.getIcon(), request.getUrl()).getBytes();

            String username = userDbRepository.getUsername(request.getBuyerId());
            log.info("Sending notification to user: {}", username);
            Subscription subscription = subscriptionRepository.findByUsername(username).get(0);

            Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getP256dh(),
                    subscription.getAuth(),
                    payload
            );

            pushService.send(notification);
            return ResponseEntity.ok("Notification sent successfully!");

        } catch (JoseException | GeneralSecurityException | IOException | ExecutionException | InterruptedException |
                 IndexOutOfBoundsException e) {
            log.error("Failed to send notification: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
        }

    }
}

