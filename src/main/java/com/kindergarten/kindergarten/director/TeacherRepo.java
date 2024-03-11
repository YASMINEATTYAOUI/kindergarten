package com.kindergarten.kindergarten.director;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//Remove @RepositoryRestResource below to disable auto REST api:
@RepositoryRestResource
public interface TeacherRepo extends CrudRepository<Teacher, Integer> {
    public List<Teacher> findByDirector(Director director);
}