package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

public interface FeedbackRequestService {
    void addFeedback(FeedbackRequest feedback);
    FeedbackRequest findFeedbackByRequest(RequestModel request);
}
