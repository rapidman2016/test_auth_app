package com.test.websocket.auth.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.websocket.auth.api.IDataTransferObject;
import com.test.websocket.auth.api.ProtocolConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

/**
 * Created by timur on 18.04.14.
 */
public abstract class AbstractMongoEntity implements IDataTransferObject {
    public static final String UPDATE_DATE_FIELD = "updateDate";
    @Id
    private String id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= ProtocolConstants.DATE_TIME_FORMAT, timezone="TIME_ZONE_UTC")
    private Date createDate = new Date();

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= ProtocolConstants.DATE_TIME_FORMAT, timezone="TIME_ZONE_UTC")
    private Date updateDate;

    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonIgnore
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "AbstractMongoEntity{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMongoEntity that = (AbstractMongoEntity) o;

        if (version != that.version) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        return result;
    }

    @JsonIgnore
    public Update getUpdate(){
        Update update = new Update();
        update.inc("version", Integer.valueOf(1));
        update.set(UPDATE_DATE_FIELD, new Date());
        exposeFieldsToSave(update);
        return update;
    }

    protected abstract void exposeFieldsToSave(Update update);


}
