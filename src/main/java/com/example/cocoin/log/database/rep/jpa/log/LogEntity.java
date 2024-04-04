package com.example.cocoin.log.database.rep.jpa.log;

import com.example.cocoin.common.database.rep.jpa.CommonDateEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_entity")
public class LogEntity extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "class_name", nullable = false, length = 255, updatable = false)
    private String className;

    @Column(name = "kind", nullable = false, length = 255, updatable = false)
    private String kind;

    @Column(name = "args", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String args;

    @Column(name = "return_obj", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String returnObj;

    @Column(name = "long_intro", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String longIntro;

    @Column(name = "short_intro", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String shortIntro;

}