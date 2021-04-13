package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.RoleModel;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
}
