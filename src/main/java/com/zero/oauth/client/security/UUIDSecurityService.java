package com.zero.oauth.client.security;

import java.util.UUID;

/**
 * Use {@code} UUID_ALGO to generate random token.
 *
 * @see java.util.UUID
 */
public final class UUIDSecurityService extends RandomSecurityService implements SecurityService {

    static final SecurityService INSTANCE = new UUIDSecurityService();

    private UUIDSecurityService() {
        this.registerMachine(new UUIDMachine());
    }

    static class UUIDMachine implements RandomMachine {

        @Override
        public String getRandom() {
            return UUID.randomUUID().toString();
        }

    }

}
