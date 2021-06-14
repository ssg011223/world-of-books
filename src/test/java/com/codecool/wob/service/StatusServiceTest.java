package com.codecool.wob.service;

import com.codecool.wob.dao.StatusDao;
import com.codecool.wob.model.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    @Mock
    private static StatusDao statusDao;

    private static StatusService statusService;

    @BeforeAll
    public static void init() {
        statusService = new StatusService(statusDao);
    }

    @Test
    public void getStatusCollectionFromJsonArr_ValidJsonArray_ReturnCollectionOfStatuses() throws JsonProcessingException {
        JSONArray arr = new JSONArray("[\n" +
                "{\n" +
                "\"id\": 1,\n" +
                "\"status_name\": \"ACTIVE\"\n" +
                "},\n" +
                "{\n" +
                "\"id\": 2,\n" +
                "\"status_name\": \"SCHEDULED\"\n" +
                "},\n" +
                "{\n" +
                "\"id\": 3,\n" +
                "\"status_name\": \"CANCELLED\"\n" +
                "}\n" +
                "]");
        Collection<Status> expected = new ArrayList<>();
        expected.add(new Status(1, "ACTIVE"));
        expected.add(new Status(2, "SCHEDULED"));
        expected.add(new Status(3, "CANCELLED"));

        Collection<Status> statuses = statusService.getStatusCollectionFromJsonArr(arr);
        assertIterableEquals(expected, statuses);
    }

    @Test
    public void getStatusCollectionFromJsonArr_InvalidJsonArray_ThrowsException() {
        JSONArray arr = new JSONArray("[{\"invalidfield\": \"value\"}]");

        assertThrows(JsonProcessingException.class, () -> statusService.getStatusCollectionFromJsonArr(arr));
    }

}