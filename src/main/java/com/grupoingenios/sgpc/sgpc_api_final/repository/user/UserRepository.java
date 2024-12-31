package com.grupoingenios.sgpc.sgpc_api_final.repository.user;

import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String userName);

    Optional<User> findByUsername(String userName);

    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<User> findByUsernameWithRol(@Param("username") String username);


}
