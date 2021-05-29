package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;

@Service
@Transactional
public class FeedbackRequestServiceImpl implements FeedbackRequestService{
    @Autowired
    FeedbackRequestDb feedbackRequestDb;

    @Override
    public void addFeedback(FeedbackRequest feedback){
        feedbackRequestDb.save(feedback);
        System.out.println("masuk nih feedbacknya");
    }

    @Override
    public FeedbackRequest findFeedbackByRequest(RequestModel request) {
        return feedbackRequestDb.findByRequest(request).get();
    }
}
