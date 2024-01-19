package com.aselsis.iot.gateway.soketconfig;

import lombok.Data;
import java.util.Map;

@Data
public class Message {
    private Map<String, String> attributes;
    private String serial;
}
