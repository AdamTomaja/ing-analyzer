package com.cydercode.ing.analyzer.markers;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MarkersRepository {

    private List<Marker> markers = new CopyOnWriteArrayList<>();

    public Marker addMarker(Marker marker) {
        marker.setId(UUID.randomUUID());
        markers.add(marker);
        return marker;
    }

    public List<Marker> getMarkers() {
        return Collections.unmodifiableList(markers);
    }

    public Optional<Marker> findByName(String markerName) {
        return markers.stream().filter(m -> m.getName().equals(markerName)).findFirst();
    }
}
