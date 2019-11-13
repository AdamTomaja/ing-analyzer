package com.cydercode.ing.analyzer;

import com.cydercode.ing.analyzer.marking.Marker;
import com.cydercode.ing.client.history.HistoryResponseData;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class Database {

    private List<Marker> markers = new CopyOnWriteArrayList<>();

    public boolean hasTransaction(HistoryResponseData.Transaction transaction) {
        return false;
    }

    public Marker addMarker(Marker marker) {
        marker.setId(UUID.randomUUID());
        markers.add(marker);
        return marker;
    }

    public List<Marker> getMarkers() {
        return Collections.unmodifiableList(markers);
    }

    private boolean hasMarker(Marker marker) {
        return markers.stream().anyMatch(m -> m.getId().equals(marker.getId()));
    }
}
