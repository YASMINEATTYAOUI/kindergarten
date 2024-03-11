package com.kindergarten.kindergarten.director;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kindergarten.kindergarten.kindergarten.KinderGarten;

import jakarta.transaction.Transactional;

//Remove @RepositoryRestResource below to disable auto REST api:
@RepositoryRestResource
public interface ClasseRepo extends CrudRepository<Classe, Integer> {
    public List<Classe> findByKindergartenOrderByKindergarten(KinderGarten kindergarten);

    public List<Classe> findByDirecteurOrderByDirecteur(Director directeur);

    public boolean existsByKindergarten(KinderGarten kindergarten);

    @Transactional
    public Long deleteByKindergarten(KinderGarten kindergarten);

}
