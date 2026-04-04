package org.bankApp.entity;

import jakarta.persistence.*;
import org.bankApp.dto.enums.TransactionStatus;
import org.bankApp.dto.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private LocalDateTime createdAt;

    public Transaction(Long id, Account fromAccount, Account toAccount, BigDecimal amount, TransactionType type, TransactionStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(fromAccount, that.fromAccount) && Objects.equals(toAccount, that.toAccount) && Objects.equals(amount, that.amount) && Objects.equals(type, that.type) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccount, toAccount, amount, type, status, createdAt);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                ", type=" + type +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
