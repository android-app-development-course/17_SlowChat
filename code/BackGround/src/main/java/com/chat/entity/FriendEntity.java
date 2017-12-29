package com.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "friend", schema = "chat", catalog = "")
public class FriendEntity {
    private int id;
    private short sourceId;
    private short targetId;
    private short sendCount;
    private int feeling;
    private Integer column6;
    private UserEntity userBySourceId;
    private UserEntity userByTargetId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "send_count", nullable = false)
    public short getSendCount() {
        return sendCount;
    }

    public void setSendCount(short sendCount) {
        this.sendCount = sendCount;
    }

    @Basic
    @Column(name = "feeling", nullable = false)
    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    @Basic
    @Column(name = "column_6", nullable = true)
    public Integer getColumn6() {
        return column6;
    }

    public void setColumn6(Integer column6) {
        this.column6 = column6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendEntity that = (FriendEntity) o;

        if (id != that.id) return false;
        if (sourceId != that.sourceId) return false;
        if (targetId != that.targetId) return false;
        if (sendCount != that.sendCount) return false;
        if (feeling != that.feeling) return false;
        if (column6 != null ? !column6.equals(that.column6) : that.column6 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) sourceId;
        result = 31 * result + (int) targetId;
        result = 31 * result + (int) sendCount;
        result = 31 * result + feeling;
        result = 31 * result + (column6 != null ? column6.hashCode() : 0);
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
