package com.grupoingenios.sgpc.sgpc_api_final.config;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.RolRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Clase de configuración para inicializar un usuario administrador predeterminado en el sistema.
 * Esta clase verifica la existencia del rol de administrador y del usuario con el nombre "ADMIN".
 * Si no existen, los crea con valores predeterminados.
 */
@Configuration
public class AdminInitializerService {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializerService.class);

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Constructor de la clase AdminInitializerService.
     *
     * @param userRepository Repositorio para manejar las operaciones de persistencia de usuarios.
     * @param rolRepository Repositorio para manejar las operaciones de persistencia de roles.
     * @param passwordEncoder Codificador de contraseñas para asegurar las credenciales de los usuarios.
     */

    public AdminInitializerService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder ){
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método que se ejecuta después de la inicialización del contenedor de Spring.
     * Este método verifica y configura un rol de administrador y un usuario administrador predeterminado
     * si no existen previamente en la base de datos.
     */
    @PostConstruct
    public void initializeAdminUser(){

        Rol rol = rolRepository.findByName(Rol.ROLE_ADMIN).orElseGet(()->{
            Rol role = new Rol();
            role.setName(Rol.ROLE_ADMIN);
            return rolRepository.save(role);
        });

        if(!userRepository.existsByUsernameIgnoreCase("ADMIN")){
            User adminUser = new User();

            adminUser.setUsername("ADMIN");
            adminUser.setPassword(passwordEncoder.encode("admin12345678"));
            adminUser.setRol(rol);

            userRepository.save(adminUser);
        }else{
            log.info("El usuario ADMIN ya existe. No se requiere acción.");
        }


    }


}
