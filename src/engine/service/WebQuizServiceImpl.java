package engine.service;

import engine.model.dto.Feedback;
import engine.model.dto.QuizDTO;
import engine.model.entity.CompletedQuiz;
import engine.model.entity.Quiz;
import engine.model.entity.User;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WebQuizServiceImpl implements WebQuizService {
    private static final String CORRECT_ANSWER_FEEDBACK_MESSAGE = "Congratulations, you're right!";
    private static final String WRONG_ANSWER_FEEDBACK_MESSAGE = "Wrong answer! Please, try again.";
    private final QuizRepository quizRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public WebQuizServiceImpl(QuizRepository quizRepository, UserDetailsServiceImpl userDetailsService, CompletedQuizRepository completedQuizRepository) {
        this.quizRepository = quizRepository;
        this.userDetailsService = userDetailsService;
        this.completedQuizRepository = completedQuizRepository;
    }

    @Override
    public Page<Quiz> getAllQuizzes(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Quiz> quizzes = quizRepository.findAll(pageable);
        return quizzes;
    }

    @Override
    public Quiz getQuizById(int id) {

        Optional<Quiz> result = quizRepository.findById(id);

        Quiz quiz = null;

        if (result.isPresent()) {
            quiz = result.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return quiz;
    }

    @Override
    public Quiz addQuiz(QuizDTO quizDTO) {

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizDTO.getTitle());
        newQuiz.setText(quizDTO.getText());
        newQuiz.setOptions(quizDTO.getOptions());
        newQuiz.setAnswer(quizDTO.getAnswer());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userDetailsService.loadUserByUsername(auth.getName());

        newQuiz.setCreatorEmail(user.getUsername());

        return quizRepository.save(newQuiz);
    }

    @Override
    public Feedback checkQuizAnswer(int id, List<Integer> answers) {

        Quiz q = this.getQuizById(id);
        List<Integer> quizAnswer = q.getAnswer();

        Collections.sort(quizAnswer);
        Collections.sort(answers);

        if (quizAnswer.size() != answers.size()) return new Feedback(false, WRONG_ANSWER_FEEDBACK_MESSAGE);

        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i) != quizAnswer.get(i)) return new Feedback(false, WRONG_ANSWER_FEEDBACK_MESSAGE);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        completedQuizRepository.save(new CompletedQuiz(q.getId(), user.getEmail()));

        return new Feedback(true, CORRECT_ANSWER_FEEDBACK_MESSAGE);
    }

    public ResponseEntity<String> deleteQuizById(int id) {
        Quiz quiz = getQuizById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (quiz.getCreatorEmail()==null || !quiz.getCreatorEmail().equals(user.getUsername())) {
            return new ResponseEntity<>("Only the creator of the quiz can delete the quiz - id: " + id, HttpStatus.FORBIDDEN);
        }

        quizRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Override
    public Page<CompletedQuiz> getCompletedQuizzes(int pageNo, int pageSize) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CompletedQuiz> completedQuizzes = completedQuizRepository.findAllByUserEmailOrderByCompletedAtDesc(
                user.getEmail(), pageable);

        return completedQuizzes;
    }
}
