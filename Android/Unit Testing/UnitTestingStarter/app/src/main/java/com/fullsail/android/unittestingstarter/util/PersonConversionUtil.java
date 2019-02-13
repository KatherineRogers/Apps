package com.fullsail.android.unittestingstarter.util;

import com.fullsail.android.unittestingstarter.object.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonConversionUtil {

    public static ArrayList<Person> getPeopleListFromJSON(JSONArray _peopleJson)
            throws JSONException {

        ArrayList<Person> people = new ArrayList<>();

        for(int i = 0; i < _peopleJson.length(); i++) {
            JSONObject personJson = _peopleJson.getJSONObject(i);
            Person person = new Person(personJson);
            people.add(person);
        }

        return people;
    }

    public static JSONArray getPeopleJSONFromList(ArrayList<Person> _people)
            throws JSONException {

        JSONArray peopleJson = new JSONArray();

        for(Person person : _people) {
            peopleJson.put(person.getPersonAsJSON());
        }

        return peopleJson;
    }
}
