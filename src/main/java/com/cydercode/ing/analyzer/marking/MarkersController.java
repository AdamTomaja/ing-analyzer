package com.cydercode.ing.analyzer.marking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarkersController {

    private final MarkersRepository markersRepository;

    public MarkersController(MarkersRepository markersRepository) {
        this.markersRepository = markersRepository;
    }


    @PostMapping("/markers")
    public Marker addMarker(@RequestBody Marker marker) {
        return markersRepository.addMarker(marker);
    }

    @GetMapping("/markers")
    public List<Marker> getMarkers() {
        return markersRepository.getMarkers();
    }
}
