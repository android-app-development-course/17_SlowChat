package com.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "friend_apply", schema = "chat", catalog = "")
public class FriendApplyEntity {
    private int id;
    private short sourceId;
    private short targetId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendApplyEntity that = (FriendApplyEntity) o;

        if (id != that.id) return false;
        if (sourceId != that.sourceId) return false;
        if (targetId != that.targetId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) sourceId;
        result = 31 * result + (int) targetId;
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
