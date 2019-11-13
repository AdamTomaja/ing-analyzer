package com.cydercode.ing.analyzer;

import com.cydercode.ing.analyzer.marking.Marker;
import com.cydercode.ing.client.EasyIng;
import com.cydercode.ing.client.accounts.AccountsResponseData;
import com.cydercode.ing.client.common.IngException;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private final Database database;
    private final EasyIng easyIng;

    public MainController(Database database, EasyIng easyIng) {
        this.database = database;
        this.easyIng = easyIng;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/today")
    public Map<String, List<HistoryResponseData.Transaction>> getTodayTransactions() throws IngException {
        Map<String, List<HistoryResponseData.Transaction>> transactions = new HashMap<>();
        easyIng.getAccounts().getAccts().getCur().getAccts().forEach(account -> {
            try {
                HistoryResponseData transactionsResponse = getTransactions(account);
                transactions.put(account.getAcct(), transactionsResponse.getTrns()
                        .stream().map(wr -> wr.getM())
                        .collect(Collectors.toList()));
            } catch (IngException e) {
                throw new RuntimeException(e);
            }
        });

        return transactions;
    }

    @PostMapping("/markers")
    public Marker addMarker(@RequestBody Marker marker) {
        return database.addMarker(marker);
    }

    @GetMapping("/markers")
    public List<Marker> getMarkers() {
        return database.getMarkers();
    }

    private HistoryResponseData getTransactions(AccountsResponseData.Account account) throws IngException {
        return easyIng.getTransactions(Collections.singletonList(account.getAcct()), "", "2019-11-13", "2019-11-13", 1000);
    }
}
