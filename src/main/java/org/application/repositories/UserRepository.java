package org.application.repositories;

import org.application.dataClasses.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData,Integer>{
    boolean getByEmailEqualsIgnoreCase(String email);
}
