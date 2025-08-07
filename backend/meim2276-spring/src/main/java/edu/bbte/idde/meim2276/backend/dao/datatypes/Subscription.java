package edu.bbte.idde.meim2276.backend.dao.datatypes;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription extends BaseEntity {
    private String endpoint;
    private String p256dh;
    private String auth;
    private String username;
}
