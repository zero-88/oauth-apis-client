package com.zero.oauth.client.core.properties.converter;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;

public class JsonConverter implements IPropertiesConverter {

    @Override
    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep flowStep) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = properties.by(flowStep).stream().collect(Collectors.toMap(IPropertyModel::getName,
                                                                                            IPropertyModel::validate));
        return gson.toJson(map);
    }

    @Override
    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep) {
        return null;
    }

}