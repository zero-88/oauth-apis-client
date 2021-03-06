package com.zero.oauth.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String BLANK = "";
    public static final String CARRIAGE_RETURN = "\r\n";
    public static final String OAUTH_SIGNATURE_METHOD_PROPERTY = "z.oauth.sec.algo.signature.method";
    public static final String OAUTH_RANDOM_TOKEN_PROPERTY = "z.oauth.sec.algo.random.token";
    public static final String OAUTH_RANDOM_TEXT_MAX_LEN_PROPERTY = "z.oauth.sec.algo.random.token.text.maxlen";
    public static final String OAUTH_RANDOM_TEXT_SYMBOL_PROPERTY = "z.oauth.sec.algo.random.token.text.symbols";
    public static final String OAUTH_SIGNATURE_RSA_PRIVATE_KEY_FILE = "z.oauth.sec.algo.rsa.private.key";

    public static final String TEXT_ALGO = "text";
    public static final String UUID_ALGO = "uuid";
    public static final String TIMESTAMP_ALGO = "timestamp";

    public static final String CLIENT_ARTIFACT = "zero-oauth-client";

}
