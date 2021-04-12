package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.UserModel;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDb extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
