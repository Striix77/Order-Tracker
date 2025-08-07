package edu.bbte.idde.meim2276.backend.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private Long buyerId;
    private String title;
    private String body;
    private String icon;
    private String url;
}