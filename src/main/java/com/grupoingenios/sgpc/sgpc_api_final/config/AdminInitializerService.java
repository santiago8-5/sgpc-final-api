package com.grupoingenios.sgpc.sgpc_api_final.config;


import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.RolRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializerService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializerService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder ){
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void initializeAdminUser(){

        // Verificar si existe el rol admin
        Rol rol = rolRepository.findByName(Rol.ROLE_ADMIN).orElseGet(()->{
            Rol role = new Rol();
            role.setName(Rol.ROLE_ADMIN);
            return rolRepository.save(role);
        });

        // verificar si existe un usuario administrador
        if(!userRepository.existsByUsernameIgnoreCase("ADMIN")){

            // Creando un nuevo ususario
            User adminUser = new User();

            // Asignando un valores iniciales al usuario
            adminUser.setUsername("ADMIN");
            adminUser.setPassword(passwordEncoder.encode("admin12345678"));

            // Asignando un rol user
            adminUser.setRol(rol);

            // Guardando el nuevo user
            userRepository.save(adminUser);

            System.out.println("El usuario administrador se creo con éxito");

        }else{
            System.out.println("EL usuario administrador ya existe");
        }


    }


}
