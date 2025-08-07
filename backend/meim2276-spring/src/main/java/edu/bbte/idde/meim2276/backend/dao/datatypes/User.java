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
public class User extends BaseEntity {

    private String username;
    private String password;
    private String theme;
    private String highContrast;
    private String language;
    private Boolean isAdmin;
}
