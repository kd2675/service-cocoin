package com.example.cocoin.log.database.rep.jpa.order;

import com.example.cocoin.common.database.rep.jpa.CommonDateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_order_entity")
public class LogOrderEntity extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "log_given", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String logGiven;

    @Column(name = "log_when", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String logWhen;

    @Column(name = "log_then", nullable = false, columnDefinition = "TEXT", updatable = false)
    private String logThen;

}