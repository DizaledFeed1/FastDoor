package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByNickname(String nickname);

//    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :roleCode")
//    List<User> findAllSellers(@Param("roleCode") String roleCode);

}
