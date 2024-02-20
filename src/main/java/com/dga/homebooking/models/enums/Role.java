package com.dga.homebooking.models.enums;


import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ROLE_USER,ROLE_BOOKER,ROLE_ALLVIEWER,ROLE_ADMIN;


    public String getAuthority() {
        return name();
    }
}
