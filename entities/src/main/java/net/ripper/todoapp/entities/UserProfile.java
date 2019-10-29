package net.ripper.todoapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User ownerUser;
    private String name;
    private String picUrl;
    private String timeZone;
    @Column(updatable = false)
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
