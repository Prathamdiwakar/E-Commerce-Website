package com.ecommerce.project.repositories;

import com.ecommerce.project.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotNull @Size(min =3, max=20) String username);

    boolean existsByEmail(@NotNull @Size(max=50) @Email String email);
}
