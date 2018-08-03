package com.zero.oauth.client.client;

import java.util.Objects;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponsePropModel;
import com.zero.oauth.client.type.HttpMethod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuth2Client<T extends OAuth2Api> implements OAuthClient<T> {

    private final T api;
    private CallbackHandler callback;

    @Override
    public <P extends IResponsePropModel> IPropertyStore<P> generateAuthorizeRedirect() {
        return null;
    }

    @Override
    public <P extends IResponsePropModel> IPropertyStore<P> fetchAccessToken() {
        return null;
    }

    @Override
    public void registerCallback(CallbackHandler callback) {
        this.callback = Objects.requireNonNull(callback);
    }

    @Override
    public <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> request(
        HttpMethod method, String url, IPropertyStore<R> reqProps) {
        return null;
    }

}
