package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Status;
import com.codecool.wob.util.ApiRequester;
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

    public Collection<Status> getStatusCollectionFromJsonArr(JSONArray arr) {
        Collection<Status> res = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Status status = new Status();
            status.setId(obj.getInt("id"));
            status.setName(obj.getString("status_name"));
            res.add(status);
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
