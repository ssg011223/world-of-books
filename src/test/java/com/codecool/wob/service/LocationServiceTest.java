package com.codecool.wob.service;

import com.codecool.wob.dao.LocationDao;
import com.codecool.wob.model.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LocationServiceTest {
    @Mock
    private static LocationDao locationDao;

    private static LocationService locationService;

    @BeforeAll
    public static void init() {
        locationService = new LocationService(locationDao);
    }

    @Test
    public void getLocationCollectionFromJsonArr_ValidJsonArray_ReturnCollectionOfLocations() throws JsonProcessingException {
        JSONArray arr = new JSONArray("[\n" +
                "{\n" +
                "\"id\": \"0fe479bb-fe39-4265-b59f-bb4ac5a060d4\",\n" +
                "\"manager_name\": \"Thorny McGenis\",\n" +
                "\"phone\": \"656-233-6237\",\n" +
                "\"address_primary\": \"8 Ramsey Hill\",\n" +
                "\"address_secondary\": null,\n" +
                "\"country\": \"Nicaragua\",\n" +
                "\"town\": \"Granada\",\n" +
                "\"postal_code\": null\n" +
                "},\n" +
                "{\n" +
                "\"id\": \"ac867cd8-c321-42ab-b179-01a4b8301a9c\",\n" +
                "\"manager_name\": \"Laure Stirley\",\n" +
                "\"phone\": \"504-782-1448\",\n" +
                "\"address_primary\": \"4092 Bunker Hill Avenue\",\n" +
                "\"address_secondary\": null,\n" +
                "\"country\": \"Kenya\",\n" +
                "\"town\": \"Konza\",\n" +
                "\"postal_code\": null\n" +
                "}]");
        Collection<Location> expected = new ArrayList<>();
        expected.add(new Location(UUID.fromString("0fe479bb-fe39-4265-b59f-bb4ac5a060d4"), "Thorny McGenis", "656-233-6237","8 Ramsey Hill",null,"Nicaragua","Granada",null));
        expected.add(new Location(UUID.fromString("ac867cd8-c321-42ab-b179-01a4b8301a9c"), "Laure Stirley", "504-782-1448","4092 Bunker Hill Avenue",null,"Kenya","Konza",null));

        Collection<Location> locations = locationService.getLocationCollectionFromJsonArr(arr);
        assertIterableEquals(expected, locations);
    }

    @Test
    public void getLocationCollectionFromJsonArr_InvalidJsonArray_ThrowsException() throws JsonProcessingException {
        JSONArray arr = new JSONArray("[{\"invalidfield\": \"value\"}]");

        assertThrows(JsonProcessingException.class, () -> locationService.getLocationCollectionFromJsonArr(arr));
    }

}