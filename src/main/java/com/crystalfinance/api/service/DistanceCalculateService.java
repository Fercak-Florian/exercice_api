package com.crystalfinance.api.service;

import com.crystalfinance.api.model.Coordinates;
import com.crystalfinance.api.repository.CoordinatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;
import net.sf.geographiclib.*;

@Slf4j
@Service
public class DistanceCalculateService {

    private CoordinatesRepository coordinatesRepository;

    public DistanceCalculateService(CoordinatesRepository coordinatesRepository){
        this.coordinatesRepository = coordinatesRepository;
    }

    public long calculateDistance(String idOne, String idTwo){
        Optional<Coordinates> optionalCoordinatesOne = coordinatesRepository.findById(idOne);
        Optional<Coordinates> optionalCoordinatesTwo = coordinatesRepository.findById(idTwo);

        Coordinates coordinatesOne = optionalCoordinatesOne.get();
        Coordinates coordinatesTwo = optionalCoordinatesTwo.get();

        GeodesicData geodesic = Geodesic.WGS84.Inverse(coordinatesOne.getLatitude(), coordinatesOne.getLongitude(), coordinatesTwo.getLatitude(), coordinatesTwo.getLongitude());

        double distanceInMeters = geodesic.s12;
        double distanceInKilometers = distanceInMeters / 1000;
        long distance = Math.round(distanceInKilometers);

        return distance;
    }
}
