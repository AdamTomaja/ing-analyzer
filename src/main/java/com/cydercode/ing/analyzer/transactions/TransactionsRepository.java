package com.cydercode.ing.analyzer.transactions;

import com.cydercode.ing.analyzer.markers.Marker;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TransactionsRepository {

    private final List<MarkedTransaction> markedTransactionList = new CopyOnWriteArrayList<>();
    private final List<HistoryResponseData.Transaction> allTransactions = new CopyOnWriteArrayList<>();

    public boolean isMarked(HistoryResponseData.Transaction transaction) {
        return markedTransactionList.stream().anyMatch(m -> m.getTransaction().getId().equals(transaction.getId()));
    }

    public void markTransaction(HistoryResponseData.Transaction transaction, Marker marker) {
        markedTransactionList.add(MarkedTransaction.builder()
                .transaction(transaction)
                .marker(marker)
                .build());
    }

    public void saveAll(List<HistoryResponseData.Transaction> transactions) {
        List<HistoryResponseData.Transaction> newTransactions = transactions.stream()
                .filter(t -> !getTransaction(t.getId()).isPresent())
                .collect(Collectors.toList());
        allTransactions.addAll(newTransactions);
    }

    public List<HistoryResponseData.Transaction> findAllByMarker(Marker marker) {
        return markedTransactionList.stream()
                .filter(t -> t.getMarker().equals(marker))
                .map(m -> m.getTransaction())
                .collect(Collectors.toList());
    }

    public Optional<HistoryResponseData.Transaction> getTransaction(String transactionId) {
        return allTransactions.stream().filter(t -> t.getId().equals(transactionId)).findFirst();
    }
}
