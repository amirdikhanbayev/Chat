package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    List<Users> findByOnline(boolean online);
    @Modifying
    @Query("UPDATE Users s set s.online = TRUE where s.id = :id")
    public void online(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Users s set s.online = FALSE where s.id = :id")
    public void offline(@Param("id") Long id);
}
