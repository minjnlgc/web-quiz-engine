package engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Setter
    private boolean success;
    @Getter
    @Setter
    private String feedback;

    public boolean getSuccess() {
        return success;
    }
}
