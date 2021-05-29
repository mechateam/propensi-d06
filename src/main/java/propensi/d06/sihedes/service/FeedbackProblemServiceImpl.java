package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;

@Service
@Transactional
public class FeedbackProblemServiceImpl implements FeedbackProblemService{
    @Autowired
    FeedbackProblemDb feedbackProblemDb;

    @Override
    public void addFeedback(FeedbackProblem feedback){
        feedbackProblemDb.save(feedback);
        System.out.println("masuk nih feedbacknya");
    }

    @Override
    public FeedbackProblem findFeedbackByProblem(ProblemModel Problem) {
        return feedbackProblemDb.findByProblem(Problem).get();
    }
}
