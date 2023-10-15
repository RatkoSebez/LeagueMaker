package com.lgm.demo.security;

import com.lgm.demo.model.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="roles")
// TODO change name of class, it is not clear
public class Role{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length=20)
    private ERole name;

    public Role(){}

    public Role(ERole name){
        this.name = name;
    }
}
