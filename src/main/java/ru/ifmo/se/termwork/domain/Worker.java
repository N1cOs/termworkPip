package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Domain object which represents a worker of the specified selection committee
 */

@Entity
@Table(name = "worker_ac")
@Data
@NamedEntityGraphs({
        @NamedEntityGraph(name = "worker.messages", attributeNodes = {
          @NamedAttributeNode("messages")
        })
})
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;

    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String password;

    @Column(name = "head_worker")
    private boolean isHeadWorker;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "worker")
    private Set<Message> messages;

}

