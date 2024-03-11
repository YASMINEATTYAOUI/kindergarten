package com.kindergarten.kindergarten.accesspermission;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//Remove @RepositoryRestResource below to disable auto REST api:
@RepositoryRestResource
public interface AccessPermissionRepo extends CrudRepository<AccessPermission, String> {
}