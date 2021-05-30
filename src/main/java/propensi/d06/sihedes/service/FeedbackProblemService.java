package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

public interface FeedbackProblemService {
    void addFeedback(FeedbackProblem feedback);
    FeedbackProblem findFeedbackByProblem(ProblemModel Problem);
}
