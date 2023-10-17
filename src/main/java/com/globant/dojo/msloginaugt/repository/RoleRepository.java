package com.globant.dojo.msloginaugt.repository;

import com.globant.dojo.msloginaugt.model.persistence.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
