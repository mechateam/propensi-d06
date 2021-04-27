package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.LogRequestModel;
import propensi.d06.sihedes.repository.LogRequestDb;

import javax.transaction.Transactional;

@Service
@Transactional
public class LogRequestServiceImpl implements LogRequestService {
    @Autowired
    LogRequestDb logRequestDb;

    @Override
    public void addLog(LogRequestModel log) {
        logRequestDb.save(log);
    }
}
