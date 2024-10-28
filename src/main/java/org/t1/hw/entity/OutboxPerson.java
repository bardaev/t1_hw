package org.t1.hw.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "outbox_person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class OutboxPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payload")
    private String payload;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OutboxPersonStatus status = OutboxPersonStatus.IN_ORDER;
}
