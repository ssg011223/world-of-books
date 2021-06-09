package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Marketplace;
import com.codecool.wob.util.ApiRequester;
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

    public Collection<Marketplace> getMarketplaceCollectionFromJsonArr(JSONArray arr) {
        Collection<Marketplace> res = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Marketplace marketplace = new Marketplace();
            marketplace.setId(obj.getInt("id"));
            marketplace.setName(obj.getString("marketplace_name"));
            res.add(marketplace);
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
