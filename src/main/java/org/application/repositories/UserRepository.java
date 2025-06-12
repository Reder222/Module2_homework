package org.application.repositories;

import jakarta.validation.constraints.NotNull;
import org.application.dataClasses.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserData,Integer>{
    boolean getByEmailEqualsIgnoreCase(String email);

    UserData searchByEmail(String email);

    List<UserData> findAllByEmail(@NotNull(message = "Email cant be null") String email);
}
