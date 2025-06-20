package testProject.tests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestService {
    public TestResult evaluate(Test test, Map<Integer, Integer> answersMap, String username) {
        List<QuestionResult> questionResults = new ArrayList<>();
        int index = 0;
        for (Question question : test.getQuestions()) {
            int userAnswer = answersMap.get(index);
            int correctAnswer = question.getCorrectIndex();
            boolean isCorrect = (userAnswer == correctAnswer);

            String text = question.getText();
            String userAnswerText = question.getOptions().get(userAnswer);
            String correctAnswerText = question.getOptions().get(correctAnswer);
            QuestionResult qr = QuestionResult.builder()
                    .questionText(text)
                    .userAnswerText(userAnswerText)
                    .correctAnswerText(correctAnswerText)
                    .isCorrect(isCorrect)
                    .build();
            questionResults.add(qr);
            index++;
        }
        int correctCount = (int) questionResults.stream().filter(QuestionResult::isCorrect).count();

        return TestResult.builder()
                .testId(test.getId())
                .testTitle(test.getTitle())
                .questionResults(questionResults)
                .totalQuestions(test.getQuestions().size())
                .correctCount(correctCount)
                .timestamp(LocalDateTime.now())
                .username(username)
                .build();
    }
}
