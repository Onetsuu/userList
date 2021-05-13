package com.listofusers.withspring.repository;

import com.listofusers.withspring.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userRepository extends JpaRepository<user, Long> {
    List<user> findByName(String name);
    List<user> findByCpf(String cpf);
}
