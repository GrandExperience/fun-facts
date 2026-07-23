package com.example.basicrestapi3;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "active")
public class ActiveFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fact_text", nullable = false)
    private String factText;

    @Column
    private String source;

    @Column(name = "posted_at", nullable = false)
    private Instant postedAt;

    @Column(name = "original_fact_id")
    private Integer originalFactId;

    public ActiveFact() {}
    // Creates an ActiveFact from a Fact - used when posting a fact
    public ActiveFact(Fact fact) {
        this.factText = fact.getFactText();
        this.source = fact.getSource();
        this.postedAt = Instant.now();
        this.originalFactId = fact.getId();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFactText() {
        return factText;
    }
    public void setFactText(String factText) {
        this.factText = factText;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public Instant getPostedAt() {
        return postedAt;
    }
    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }
    public Integer getOriginalFactId() {
        return originalFactId;
    }
    public void setOriginalFactId(Integer originalFactId) {
        this.originalFactId = originalFactId;
    }
}