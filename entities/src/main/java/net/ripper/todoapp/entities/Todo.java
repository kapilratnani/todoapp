package net.ripper.todoapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean doneStatus;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User ownerUser;
}
