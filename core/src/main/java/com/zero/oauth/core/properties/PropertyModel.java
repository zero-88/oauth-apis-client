package com.zero.oauth.core.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.zero.oauth.core.LoggerFactory;
import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.type.OAuthVersion;
import com.zero.oauth.core.utils.Strings;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Default Property.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public abstract class PropertyModel implements IPropertyModel {

    @Getter
    @ToString.Include
    private final OAuthVersion version;
    @Getter
    @EqualsAndHashCode.Include
    @ToString.Include
    private final String name;
    private Object value;
    private Object serializeData;
    @ToString.Include
    private Constraint constraint = Constraint.OPTIONAL;
    @SuppressWarnings("rawtypes")
    @Getter(value = AccessLevel.PROTECTED)
    private Function func;
    @Getter
    private List<HttpPlacement> availablePlacements = Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY,
                                                                    HttpPlacement.BODY);

    protected <P extends PropertyModel> PropertyModel(P property) {
        this.version = property.getVersion();
        this.name = property.getName();
        this.value = property.getValue();
        this.constraint = property.isRequired()
                          ? Constraint.REQUIRED
                          : property.isOptional() ? Constraint.OPTIONAL : Constraint.RECOMMENDATION;
        this.func = property.getFunc();
        this.availablePlacements = property.getAvailablePlacements();
    }

    /**
     * Mark property is required to further error validation.
     *
     * @param <T> Any type of {@code Property Model}
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T require() {
        this.constraint = Constraint.REQUIRED;
        return (T) this;
    }

    /**
     * Mark property is recommendation to further warn validation.
     *
     * @param <T> Any type of {@code Property Model}
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T recommend() {
        this.constraint = Constraint.RECOMMENDATION;
        return (T) this;
    }

    public Object getValue() {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T setValue(Object value) {
        this.value = value;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object serialize() {
        if (isComputeValue()) {
            this.serializeData = Objects.isNull(serializeData) ? func.apply(this.getValue()) : serializeData;
        } else {
            this.serializeData = this.getValue();
        }
        if (Objects.isNull(serializeData) || Strings.isBlank(serializeData.toString())) {
            if (this.isRequired()) {
                throw new OAuthParameterException("Missing required of property getName: " + this.getName());
            }
            if (this.isRecommendation()) {
                LoggerFactory.logger().warn(
                    "It is recommendation to add property '{0}' when sending OAuth request. " +
                    "Check REST APIs docs for more details.", this.getName());
            }
        }
        return serializeData;
    }

    @Override
    public <T extends IPropertyModel> T duplicate(Object value) {
        return this.duplicate().setValue(value);
    }

    @Override
    public boolean isComputeValue() {
        return Objects.nonNull(this.func);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IPropertyModel, I, R> T registerFunction(Function<I, R> func) {
        this.func = func;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IPropertyModel> T registerPlacements(HttpPlacement... placements) {
        this.availablePlacements = Arrays.asList(placements);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T constraint(Constraint constraint) {
        this.constraint = Objects.requireNonNull(constraint);
        return (T) this;
    }

    public boolean isRequired() {
        return this.constraint == Constraint.REQUIRED;
    }

    public boolean isRecommendation() {
        return this.constraint == Constraint.RECOMMENDATION;
    }

    public boolean isOptional() {
        return this.constraint == Constraint.OPTIONAL;
    }

    protected abstract <T extends IPropertyModel> T duplicate();

}
