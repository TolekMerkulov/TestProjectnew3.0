package testProject.tests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResult {
    String questionText;
    String userAnswerText;
    String correctAnswerText;
    boolean isCorrect;
}
