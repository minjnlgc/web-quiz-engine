package engine.service;

import engine.model.entity.CompletedQuiz;
import engine.model.entity.Quiz;
import engine.model.dto.QuizDTO;
import engine.model.dto.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WebQuizService {
    Page<Quiz> getAllQuizzes(int pageNo, int pageSize);
    Quiz getQuizById(int id);
    Quiz addQuiz(QuizDTO quizDTO);
    Feedback checkQuizAnswer(int id, List<Integer> answers);
    ResponseEntity<String> deleteQuizById(int id);
    Page<CompletedQuiz> getCompletedQuizzes(int pageNo, int pageSize);
}
