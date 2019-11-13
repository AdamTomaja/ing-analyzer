package com.cydercode.ing.analyzer.stats;

import com.cydercode.ing.analyzer.markers.Marker;
import com.cydercode.ing.analyzer.markers.MarkersRepository;
import com.cydercode.ing.analyzer.transactions.TransactionsRepository;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StatsController {

    private final TransactionsRepository transactionsRepository;
    private final MarkersRepository markersRepository;

    public StatsController(TransactionsRepository transactionsRepository, MarkersRepository markersRepository) {
        this.transactionsRepository = transactionsRepository;
        this.markersRepository = markersRepository;
    }

    @GetMapping("/stats")
    public Statistics statistics() {
        Map<Marker, Double> statsMap = new HashMap<>();

        markersRepository.getMarkers().forEach(marker -> {
            DoubleSummaryStatistics collect = transactionsRepository.findAllByMarker(marker).stream().collect(Collectors.summarizingDouble(HistoryResponseData.Transaction::getAmt));
            statsMap.put(marker, collect.getSum());
        });

        return Statistics.builder()
                .markersSummary(statsMap)
                .build();
    }
}
