package DealerStat.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, TRADER, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
