package com.listofusers.withspring.repository;

import com.listofusers.withspring.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Long> {
}
