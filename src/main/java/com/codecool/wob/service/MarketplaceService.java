package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Marketplace;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class MarketplaceService {
    private Dao<Marketplace> marketplaceDao;

    public void saveData(Collection<Marketplace> marketplaces) throws SQLException {
        marketplaceDao.save(marketplaces);
    }

    public Collection<Marketplace> getMarketplaceCollectionFromJsonArr(JSONArray arr) throws JsonProcessingException {
        Collection<Marketplace> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            res.add(objectMapper.readValue(obj.toString(), Marketplace.class));
        }

        return res;
    }

    public JSONArray fetchMarketplaceJsonData() throws IOException {
        return ApiRequester.getJsonArrData(new URL("https://my.api.mockaroo.com/marketplace?key=63304c70"));
    }

    public void run() throws IOException, SQLException {
        JSONArray arr = this.fetchMarketplaceJsonData();
        Collection<Marketplace> marketplaces = this.getMarketplaceCollectionFromJsonArr(arr);
        this.saveData(marketplaces);
    }
}
