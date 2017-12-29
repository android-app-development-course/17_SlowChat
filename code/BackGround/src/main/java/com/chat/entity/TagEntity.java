package com.chat.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tag", schema = "chat", catalog = "")
public class TagEntity {
    private short id;
    private String name;
    private Collection<UserTagEntity> userTagsById;

    @Id
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != tagEntity.id) return false;
        if (name != null ? !name.equals(tagEntity.name) : tagEntity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tagByTagId")
    public Collection<UserTagEntity> getUserTagsById() {
        return userTagsById;
    }

    public void setUserTagsById(Collection<UserTagEntity> userTagsById) {
        this.userTagsById = userTagsById;
    }
}
