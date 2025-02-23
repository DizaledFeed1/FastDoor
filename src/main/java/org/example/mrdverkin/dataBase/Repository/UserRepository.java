package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
