package testProject.tests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
    String testId;
    String testTitle;
    List<QuestionResult> questionResults;
    int totalQuestions;
    int correctCount;
    private String userId;
    private LocalDateTime timestamp;
    private String username;

}
