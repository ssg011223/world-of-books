package com.codecool.wob.service;

import com.codecool.wob.dao.MarketplaceDao;
import com.codecool.wob.model.Marketplace;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MarketplaceServiceTest {
    @Mock
    private static MarketplaceDao marketplaceDao;

    private static MarketplaceService marketplaceService;

    @BeforeAll
    public static void init() {
        marketplaceService = new MarketplaceService(marketplaceDao);
    }

    @Test
    public void getMarketplaceCollectionFromJsonArr_ValidJsonArray_ReturnCollectionOfMarketplaces() throws JsonProcessingException {
        JSONArray arr = new JSONArray("[\n" +
                "{\n" +
                "\"id\": 1,\n" +
                "\"marketplace_name\": \"EBAY\"\n" +
                "},\n" +
                "{\n" +
                "\"id\": 2,\n" +
                "\"marketplace_name\": \"AMAZON\"\n" +
                "}\n" +
                "]");
        Collection<Marketplace> expected = new ArrayList<>();
        expected.add(new Marketplace(1, "EBAY"));
        expected.add(new Marketplace(2, "AMAZON"));

        Collection<Marketplace> marketplaces = marketplaceService.getMarketplaceCollectionFromJsonArr(arr);
        assertIterableEquals(expected, marketplaces);
    }

    @Test
    public void getMarketplaceCollectionFromJsonArr_InvalidJsonArray_ThrowsException() {
        JSONArray arr = new JSONArray("[{\"invalidfield\": \"value\"}]");

        assertThrows(JsonProcessingException.class, () -> marketplaceService.getMarketplaceCollectionFromJsonArr(arr));
    }
}