package com.kindergarten.kindergarten.stream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//Remove @RepositoryRestResource below to disable auto REST api:
@RepositoryRestResource
public interface StreamKeysRepo extends CrudRepository<StreamKeys, String> {
    StreamKeys findByEmailParentAndIdEnfant(String emailp, Integer ide);
}
