package edu.bbte.idde.meim2276.backend.dao.repositories;

import edu.bbte.idde.meim2276.backend.dao.datatypes.Subscription;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Profile("DB")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Subscription s WHERE s.auth= :auth")
    void deleteByAuth(String auth);

}
