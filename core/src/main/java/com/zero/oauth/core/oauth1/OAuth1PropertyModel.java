package com.zero.oauth.core.oauth1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.PropertyModel;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.OAuthVersion;

abstract class OAuth1PropertyModel extends PropertyModel implements OAuth1PropertyMatcher {

    private final Map<FlowStep, Constraint> steps = new HashMap<>();

    OAuth1PropertyModel(String name) {
        super(OAuthVersion.V1, name);
    }

    OAuth1PropertyModel(OAuth1PropertyModel property) {
        super(property);
        this.steps.putAll(property.getMapping());
    }

    @SuppressWarnings("unchecked")
    public <T extends OAuth1PropertyModel> T declare(FlowStep step, Constraint constraint) {
        Objects.requireNonNull(step, "OAuth flow step cannot be null");
        if (!this.getVersion().isEqual(step.getVersion())) {
            throw new OAuthParameterException(
                "Step " + step.name() + " isn't supported in OAuth v" + this.getVersion());
        }
        this.steps.put(step, constraint);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OAuth1PropertyModel> T match(FlowStep step) {
        return this.steps.containsKey(step) ? (T) this : null;
    }

    /**
     * Required value depends on OAuth flow step. It is not capable to use this method.
     *
     * @throws UnsupportedOperationException Raise exception
     * @deprecated Use {@link #declare(FlowStep)}
     */
    @Override
    @Deprecated
    public <T extends IPropertyModel> T require() {
        throw new UnsupportedOperationException("Required value depends on flow step.");
    }

    /**
     * Recommendation value depends on OAuth flow step. It is not capable to use this method.
     *
     * @param <T> Type of implementation of property model
     * @throws UnsupportedOperationException Raise exception
     * @deprecated Use {@link #declare(FlowStep, Constraint)}
     */
    @Override
    @Deprecated
    public <T extends IPropertyModel> T recommend() {
        throw new UnsupportedOperationException("Recommendation value depends on flow step.");
    }

    Map<FlowStep, Constraint> getMapping() {
        return Collections.unmodifiableMap(this.steps);
    }

}
