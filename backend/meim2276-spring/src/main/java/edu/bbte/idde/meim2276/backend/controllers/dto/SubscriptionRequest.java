package edu.bbte.idde.meim2276.backend.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.martijndwars.webpush.Subscription;

@Getter
@Setter
public class SubscriptionRequest {
    @NotNull
    private String endpoint;
    @NotNull
    private Subscription.Keys keys;
    @NotNull
    private String username;


}
