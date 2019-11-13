package com.cydercode.ing.analyzer.transactions;

import com.cydercode.ing.client.EasyIng;
import com.cydercode.ing.client.accounts.AccountsResponseData;
import com.cydercode.ing.client.common.IngException;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TransactionsController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final EasyIng easyIng;
    private final TransactionsRepository transactionsRepository;

    public TransactionsController(EasyIng easyIng, TransactionsRepository transactionsRepository) {
        this.easyIng = easyIng;
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping("/new")
    public Map<String, List<HistoryResponseData.Transaction>> getTodayTransactions() throws IngException {
        Map<String, List<HistoryResponseData.Transaction>> transactions = new HashMap<>();
        easyIng.getAccounts().getAccts().getCur().getAccts().forEach(account -> {
            try {
                HistoryResponseData transactionsResponse = getTransactions(account);
                transactions.put(account.getAcct(), transactionsResponse.getTrns()
                        .stream().map(wr -> wr.getM())
                        .filter(trn -> !transactionsRepository.isMarked(trn))
                        .collect(Collectors.toList()));
            } catch (IngException e) {
                throw new RuntimeException(e);
            }
        });

        transactions.forEach((acc, trns) -> transactionsRepository.saveAll(trns));
        return transactions;
    }

    private HistoryResponseData getTransactions(AccountsResponseData.Account account) throws IngException {
        String today = getTodayDate();
        return easyIng.getTransactions(Collections.singletonList(account.getAcct()), "", today, today, 1000);
    }

    private String getTodayDate() {
        return LocalDate.now().format(formatter);
    }
}
