package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.ArtikelModel;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArticleDb extends JpaRepository<ArtikelModel, Long> {
}
