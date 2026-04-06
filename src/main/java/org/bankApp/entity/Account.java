package org.bankApp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private BigDecimal balance;
    private String currency;
    private Long version;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> receivedTransactions;

    public Account(Long id, Users user, BigDecimal balance, String currency, Long version, LocalDateTime createdAt, List<Transaction> sentTransactions, List<Transaction> receivedTransactions) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.currency = currency;
        this.version = version;
        this.createdAt = createdAt;
        this.sentTransactions = sentTransactions;
        this.receivedTransactions = receivedTransactions;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(user, account.user) && Objects.equals(balance, account.balance) && Objects.equals(currency, account.currency) && Objects.equals(version, account.version) && Objects.equals(createdAt, account.createdAt) && Objects.equals(sentTransactions, account.sentTransactions) && Objects.equals(receivedTransactions, account.receivedTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, balance, currency, version, createdAt, sentTransactions, receivedTransactions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", users=" + user +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", sentTransactions=" + sentTransactions +
                ", receivedTransactions=" + receivedTransactions +
                '}';
    }
}
