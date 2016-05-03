package com.test.websocket.auth.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by timur on 02.05.16.
 */
public class ProtocolMessage implements IDataTransferObject {
    private MessageType type;
    @JsonProperty(value = "sequence_id")
    private String sequenceId;
    private Map<String,String> data;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProtocolMessage that = (ProtocolMessage) o;

        return getSequenceId() != null ? getSequenceId().equals(that.getSequenceId()) : that.getSequenceId() == null;
    }

    @Override
    public int hashCode() {
        return getSequenceId() != null ? getSequenceId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProtocolMessage{" +
                "type=" + type +
                ", sequenceId='" + sequenceId + '\'' +
                '}';
    }
}
