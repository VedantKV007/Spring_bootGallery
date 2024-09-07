package Devvep.project_task.Repo;

import Devvep.project_task.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientNumber(String clientNumber);
    boolean existsByClientNumber(String clientNumber);
    void deleteByClientNumber(String clientNumber);
}