package com.chat.entity;

import javax.persistence.*;

@Entity
public class Friend {
    private int id;
    private short sendCount;
    private int feeling;
    private User friend;
    private User user;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @ManyToOne
    public User getFriend() {
        return friend;
    }

    public void setFriend(User targetUser) {
        this.friend = targetUser;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User sourceUser) {
        this.user = sourceUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (id != friend.id) return false;
        if (sendCount != friend.sendCount) return false;
        if (feeling != friend.feeling) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) sendCount;
        result = 31 * result + feeling;
        return result;
    }
}
