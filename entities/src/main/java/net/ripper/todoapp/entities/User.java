package net.ripper.todoapp.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "todo_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @OneToMany(mappedBy = "ownerUser")
    private Set<Todo> todoEntries;

    @OneToMany(mappedBy = "ownerUser")
    private Set<UserProfile> profiles;
}
