package com.gts.backgts.repository;

import com.gts.backgts.entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmailUsers(String emailUsers);

    @Query("select u from Users u left join fetch u.roles")
    List<Users> findAllWithRoles();

    @Query("""
            select u
            from Users u
            left join fetch u.roles
            where u.username = :username
            """)
    Optional<Users> findByUsernameWithRoles(@Param("username") String username);
}
