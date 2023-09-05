package engine.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;

    @JsonIgnore
    private String creatorEmail;

    @Size(min = 2)
    @NotNull
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER) //EAGER, fetch -> the strategy when load it
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @ElementCollection(targetClass = Integer.class, fetch = FetchType.LAZY) // new
    private List<Integer> answer = new ArrayList<>();

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }


}
