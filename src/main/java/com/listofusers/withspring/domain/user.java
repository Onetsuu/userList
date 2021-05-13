package com.listofusers.withspring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;
    @NotNull
    @NotEmpty
    @Column(name = "cpf")
    private String cpf;
    @NotNull
    @NotEmpty
    @Column(name = "rg")
    private String rg;
}
