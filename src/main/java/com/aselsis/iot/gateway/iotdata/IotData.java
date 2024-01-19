package com.aselsis.iot.gateway.iotdata;

import com.aselsis.iot.gateway.soketconfig.Message;
import io.hypersistence.utils.hibernate.type.json.JsonBlobType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import java.io.Serializable;
import java.util.Map;

@Data
@Entity
public class IotData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String serial;

    @Column
    private String room;

    @Column
    @Type(JsonBlobType.class)
    private Map<String, String> attributes;

    public static IotData create(Message message, String room) {
        IotData ioTData = new IotData();
        ioTData.serial = message.getSerial();
        ioTData.attributes = message.getAttributes();
        ioTData.room = room;
        return ioTData;
    }
    public static IotData update(IotData iotData, Message message){
        iotData.attributes = message.getAttributes();
        return iotData;
    }
}
