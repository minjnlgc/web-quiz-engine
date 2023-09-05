package engine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CompletedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @JsonProperty("id")
    private int quizId;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime completedAt;

    @JsonIgnore
    private String userEmail;

    public CompletedQuiz(int quizId, String userEmail) {
        this.quizId = quizId;
        this.userEmail = userEmail;
    }
}
