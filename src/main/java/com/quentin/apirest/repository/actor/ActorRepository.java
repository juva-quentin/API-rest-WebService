package com.quentin.apirest.repository.actor;

import com.quentin.apirest.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
