package com.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_tag", schema = "chat", catalog = "")
public class UserTag {
    private int id;
    private User userByUserId;
    private Tag tagByTagId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTag userTag = (UserTag) o;

        if (id != userTag.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    public Tag getTagByTagId() {
        return tagByTagId;
    }

    public void setTagByTagId(Tag tagByTagId) {
        this.tagByTagId = tagByTagId;
    }
}
