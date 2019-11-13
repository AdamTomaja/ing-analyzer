package com.cydercode.ing.analyzer.marking;

import com.cydercode.ing.analyzer.markers.Marker;
import com.cydercode.ing.analyzer.markers.MarkersRepository;
import com.cydercode.ing.analyzer.transactions.TransactionsRepository;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MarkingController {

    private final MarkersRepository markersRepository;
    private final TransactionsRepository transactionsRepository;

    public MarkingController(MarkersRepository markersRepository, TransactionsRepository transactionsRepository) {
        this.markersRepository = markersRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @PostMapping("/mark")
    public void mark(@RequestBody MarkingRequest markingRequest) {
        Optional<Marker> marker = markersRepository.findByName(markingRequest.getMarkerName());
        Optional<HistoryResponseData.Transaction> transaction = transactionsRepository.getTransaction(markingRequest.getTransactionId());

        transaction.ifPresentOrElse(trn -> {
            marker.ifPresentOrElse(mrk -> {
                transactionsRepository.markTransaction(trn, mrk);
            }, () -> {
                throw new RuntimeException("Marker not found");
            });
        }, () -> {
            throw new RuntimeException("Transaction not found");
        });
    }
}
