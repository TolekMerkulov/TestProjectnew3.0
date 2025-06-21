package testProject.tests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResult {
    String testId;
    String testTitle;
    List<QuestionResult> questionResults;
    int totalQuestions;
    int correctCount;
    private String userId;
    private LocalDateTime timestamp;
    private String username;
    private String resultId;

    public Date getTimestampAsDate() {
        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }

}
