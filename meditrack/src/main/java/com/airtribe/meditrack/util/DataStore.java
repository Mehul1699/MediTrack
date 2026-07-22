package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.MedicalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore<T extends MedicalEntity> {

    private List<T> data = new ArrayList<>();
    private Map<Integer, T> idMap = new HashMap<>();

    public void add(T obj) {
        data.add(obj);
        idMap.put(obj.getId(), obj);
    }

    public T getById(int id) {
        return idMap.get(id);
    }

    public List<T> getAll() {
        return data;
    }

    public void remove(T obj) {
        data.remove(obj);
        idMap.remove(obj.getId());
    }

}
