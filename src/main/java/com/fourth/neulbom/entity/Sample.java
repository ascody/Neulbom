package com.fourth.neulbom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Sample {
    @Id
    @GeneratedValue
    private Integer id;
    private String unit;
    private String type;
    private String script;
}
