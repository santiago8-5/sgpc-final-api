package com.grupoingenios.sgpc.sgpc_api_final.repository.user;

import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositorio para la entidad `User`.
 * Proporciona métodos para realizar operaciones sobre los usuarios del sistema, como la validación de existencia y la recuperación de información de los usuarios.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verifica si existe un usuario con el nombre de usuario dado, ignorando mayúsculas y minúsculas.
     *
     * @param userName El nombre de usuario a verificar.
     * @return `true` si el nombre de usuario ya existe, de lo contrario `false`.
     */
    boolean existsByUsernameIgnoreCase(String userName);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario del usuario a buscar.
     * @return Un objeto `Optional<User>` que contiene el usuario encontrado, o está vacío si no se encuentra el usuario.
     */
    Optional<User> findByUsername(String userName);

    /**
     * Busca un usuario por su nombre de usuario, recuperando también el rol del usuario.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return Un objeto `Optional<User>` que contiene el usuario encontrado con su rol asociado, o está vacío si no se encuentra el usuario.
     */
    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<User> findByUsernameWithRol(@Param("username") String username);


}
