package org.bankApp.saver;

import org.bankApp.dto.request.TransferRequest;
import org.bankApp.entity.Account;
import org.bankApp.entity.Transaction;
import org.bankApp.enums.TransactionStatus;
import org.bankApp.enums.TransactionType;
import org.bankApp.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionSaver {

    private final TransactionRepository transactionRepository;

    public TransactionSaver(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(TransferRequest request, Account fromAccount, Account toAccount){
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setType(TransactionType.TRANSFER);
        transactionRepository.save(transaction);
    }
}
