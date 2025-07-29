package com.luizroberto.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luizroberto.todosimple.models.User;
import java.util.List;






@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    @Transactional(readOnly = true)
    User findByUsername(String username);
   
}
