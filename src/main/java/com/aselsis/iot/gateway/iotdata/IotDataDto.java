package com.aselsis.iot.gateway.iotdata;


import lombok.Data;
import java.util.Map;

@Data
public class IotDataDto {
    private String serial;
    private String room;
    private Map<String, String> attributes;
}
