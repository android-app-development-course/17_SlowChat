package com.chat.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "message", schema = "chat", catalog = "")
public class MessageEntity {
    private long id;
    private String text;
    private short sourceId;
    private short targetId;
    private byte status;
    private Time time;
    private UserEntity userBySourceId;
    private UserEntity userByTargetId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "text", nullable = false, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "source_id", nullable = false)
    public short getSourceId() {
        return sourceId;
    }

    public void setSourceId(short sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "target_id", nullable = false)
    public short getTargetId() {
        return targetId;
    }

    public void setTargetId(short targetId) {
        this.targetId = targetId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (id != that.id) return false;
        if (sourceId != that.sourceId) return false;
        if (targetId != that.targetId) return false;
        if (status != that.status) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (int) sourceId;
        result = 31 * result + (int) targetId;
        result = 31 * result + (int) status;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserBySourceId() {
        return userBySourceId;
    }

    public void setUserBySourceId(UserEntity userBySourceId) {
        this.userBySourceId = userBySourceId;
    }

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByTargetId() {
        return userByTargetId;
    }

    public void setUserByTargetId(UserEntity userByTargetId) {
        this.userByTargetId = userByTargetId;
    }
}
