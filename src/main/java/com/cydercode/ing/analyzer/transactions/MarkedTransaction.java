package com.cydercode.ing.analyzer.transactions;

import com.cydercode.ing.analyzer.markers.Marker;
import com.cydercode.ing.client.history.HistoryResponseData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkedTransaction {

    private HistoryResponseData.Transaction transaction;
    private Marker marker;
}
