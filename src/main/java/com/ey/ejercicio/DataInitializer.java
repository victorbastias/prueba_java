package com.ey.ejercicio;

import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.repository.RolesRepository;
import com.ey.util.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen roles
        if (rolesRepository.count() == 0) {
            // Crear y guardar los roles
            rolesRepository.save(new Roles(RolEnum.ADMIN.name()));
            rolesRepository.save(new Roles(RolEnum.NORMAL.name()));
        }
    }
}
