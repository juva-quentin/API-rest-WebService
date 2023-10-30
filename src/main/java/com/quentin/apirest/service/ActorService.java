package com.quentin.apirest.service;

import com.quentin.apirest.entity.Actor;
import com.quentin.apirest.entity.ActorRequest;
import com.quentin.apirest.repository.actor.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Page<Actor> getAllActors(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    public Actor saveActor(ActorRequest actorRequest) {
        Actor actor = new Actor();
        actor.setActorName(actorRequest.getActorName());
        actor.setActorLastName(actorRequest.getActorLastName());
        actor.setBirthDate(actorRequest.getBirthDate());
        return actorRepository.save(actor);
    }
    public Actor updateActor(Long id, ActorRequest updatedActor) {
        Actor existingActor = actorRepository.findById(id).orElse(null);
        if (existingActor != null) {
            existingActor.setActorName(updatedActor.getActorName());
            existingActor.setActorLastName(updatedActor.getActorLastName());
            existingActor.setBirthDate(updatedActor.getBirthDate());
            return actorRepository.save(existingActor);
        }
        return null;
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

}