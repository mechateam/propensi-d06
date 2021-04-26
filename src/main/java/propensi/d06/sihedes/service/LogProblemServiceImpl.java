package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.LogProblemModel;
import propensi.d06.sihedes.repository.LogProblemDb;

import javax.transaction.Transactional;

@Service
@Transactional
public class LogProblemServiceImpl implements LogProblemService {
    @Autowired
    LogProblemDb logProblemDb;

    @Override
    public void addLog(LogProblemModel log) {
        logProblemDb.save(log);
    }
}
