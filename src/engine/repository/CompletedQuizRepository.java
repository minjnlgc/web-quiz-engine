package engine.repository;

import engine.model.entity.CompletedQuiz;
import engine.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Integer> {
    Page<CompletedQuiz> findAllByUserEmailOrderByCompletedAtDesc(String userEmail, Pageable pageable);
}
