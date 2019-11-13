package com.cydercode.ing.analyzer.stats;

import com.cydercode.ing.analyzer.markers.Marker;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Statistics {

    private Map<Marker, Double> markersSummary;

}
