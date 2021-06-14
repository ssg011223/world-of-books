package com.codecool.wob.service;

import com.codecool.wob.dao.LocationDao;
import com.codecool.wob.model.Location;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class LocationService {
    private final LocationDao locationDao;

    public void saveData(Collection<Location> locations) {
        locationDao.save(locations);
    }

    public Collection<Location> getLocationCollectionFromJsonArr(JSONArray arr) throws JsonProcessingException {
        Collection<Location> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            res.add(objectMapper.readValue(obj.toString(), Location.class));
        }

        return res;
    }

    public JSONArray fetchLocationJsonData() throws IOException {
        return ApiRequester.getJsonArrData(new URL("https://my.api.mockaroo.com/location?key=63304c70"));
    }

    public void run() throws IOException {
        JSONArray arr = this.fetchLocationJsonData();
        Collection<Location> locations = this.getLocationCollectionFromJsonArr(arr);
        this.saveData(locations);
    }
}
