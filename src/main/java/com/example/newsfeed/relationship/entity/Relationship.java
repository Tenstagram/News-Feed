package com.example.newsfeed.relationship.entity;

import com.example.newsfeed.member.entity.BaseEntity;
import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "relationship")
public class Relationship extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RelationshipStatus status;

    public Relationship(Member sender, Member receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void updateStatus(RelationshipStatus status) {
        this.status = status;
    }
}
