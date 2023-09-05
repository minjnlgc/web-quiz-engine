package engine;

import engine.model.entity.CompletedQuiz;
import engine.model.entity.User;
import engine.model.pojo.AnswerPOJO;
import engine.model.dto.Feedback;
import engine.model.entity.Quiz;
import engine.model.dto.QuizDTO;
import engine.service.WebQuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebQuizRestController {
    private final WebQuizServiceImpl webQuizService;

    @Autowired
    public WebQuizRestController(WebQuizServiceImpl webQuizService) {
        this.webQuizService = webQuizService;
    }

    @GetMapping("/quizzes")
    public Page<Quiz> getAllQuiz(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return webQuizService.getAllQuizzes(page, pageSize);
    }

    @PostMapping("/quizzes")
    public Quiz addQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        return webQuizService.addQuiz(quizDTO);
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        return webQuizService.getQuizById(id);
    }

    @PostMapping("/quizzes/{id}/solve")
    public Feedback answerQuiz(@PathVariable int id, @RequestBody AnswerPOJO answer) {
        return webQuizService.checkQuizAnswer(id, answer.getAnswer());
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable int id) {
        return webQuizService.deleteQuizById(id);
    }

    @GetMapping("/quizzes/completed")
    public Page<CompletedQuiz> getCompletedQuizzes(
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return webQuizService.getCompletedQuizzes(page, pageSize);
    }

}
