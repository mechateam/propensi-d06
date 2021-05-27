package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.*;

import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FeedbackProblemDb extends JpaRepository<FeedbackProblem, Long> {
    Optional<FeedbackProblem> findByProblem(ProblemModel problem);
}
