package com.simonenko.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "chat_state")
public class ChatState {
    @Id
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "state")
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected ChatState() {}

    public ChatState(Long chat_id, String state) {
        this.id = chat_id;
        this.state = state;
    }
}