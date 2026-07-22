package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable<T> {

    T search(int id);

    List<T> search(String name);

}
