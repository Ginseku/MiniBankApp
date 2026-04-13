package org.bankApp.saver;


import org.bankApp.dto.request.TransferRequest;
import org.bankApp.entity.ApiLog;
import org.bankApp.enums.ActionStatus;
import org.bankApp.repository.ApiLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LogSaver {

    private final ApiLogRepository apiLogRepository;

    public LogSaver(ApiLogRepository apiLogRepository) {
        this.apiLogRepository = apiLogRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLogWhenFailed(ApiLog log, TransferRequest request){
        log.setAmount(request.amount());
        log.setFromAccountId(request.from_account());
        log.setToAccountId(request.to_account());
        log.setStatus(ActionStatus.FAILED);
        log.setMessage("Transaction failed");
        apiLogRepository.save(log);
    }

    public void saveLogWhenSuccess(ApiLog log, TransferRequest request){
        log.setAmount(request.amount());
        log.setFromAccountId(request.from_account());
        log.setToAccountId(request.to_account());
        log.setStatus(ActionStatus.SUCCESSFUL);
        log.setMessage("Transaction success");
        apiLogRepository.save(log);
    }
}
