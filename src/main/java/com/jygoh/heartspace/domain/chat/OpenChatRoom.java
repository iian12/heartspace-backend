package com.jygoh.heartspace.domain.chat;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private int userCount;
    private int maxUserCount;

    @ElementCollection
    private List<Long> participantIds;

    @Builder
    public OpenChatRoom(String title, String description, int maxUserCount, List<Long> participantIds) {
        this.title = title;
        this.description = description;
        this.userCount = 1;
        this.maxUserCount = maxUserCount;
        this.participantIds = participantIds;
    }

    public void updateUserCount() {
        this.userCount++;
    }
}
