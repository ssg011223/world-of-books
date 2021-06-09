package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Status;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class StatusService {
    private Dao<Status> statusDao;

    public void saveData(Collection<Status> statuses) {
        statusDao.save(statuses);
    }

    public Collection<Status> getStatusCollectionFromJsonArr(JSONArray arr) throws JsonProcessingException {
        Collection<Status> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            res.add(objectMapper.readValue(obj.toString(), Status.class));
        }

        return res;
    }

    public JSONArray fetchStatusJsonData() throws IOException {
        return ApiRequester.getJsonArrData(new URL("https://my.api.mockaroo.com/listingStatus?key=63304c70"));
    }

    public void run() throws IOException {
        JSONArray arr = this.fetchStatusJsonData();
        Collection<Status> statuses = this.getStatusCollectionFromJsonArr(arr);
        this.saveData(statuses);
    }
}
