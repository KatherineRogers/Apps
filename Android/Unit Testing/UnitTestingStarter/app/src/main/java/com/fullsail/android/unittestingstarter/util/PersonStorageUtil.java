package com.fullsail.android.unittestingstarter.util;


import android.content.Context;

import com.fullsail.android.unittestingstarter.object.Person;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PersonStorageUtil {

    private static final String FILE_NAME = "people.json";
    private static final String FILE_FOLDER = "json";

    public static void savePerson(Context _context, Person _person) {
        ArrayList<Person> people = loadPeople(_context);
        people.add(_person);
        savePeople(_context, people);
    }

    private static void savePeople(Context _context, ArrayList<Person> _people) {
        JSONArray peopleJson = null;
        try {
            peopleJson = PersonConversionUtil.getPeopleJSONFromList(_people);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        if(peopleJson == null) {
            throw new IllegalStateException("Problem formatting JSON");
        }

        File externalStorage = _context.getExternalFilesDir(FILE_FOLDER);
        File personFile = new File(externalStorage, FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(personFile);
            fos.write(peopleJson.toString().getBytes());
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletePerson(Context _context, Person _person) {
        ArrayList<Person> people = loadPeople(_context);
        while(people.remove(_person));
        savePeople(_context, people);
    }

    public static ArrayList<Person> loadPeople(Context _context) {

        String data = null;

        File externalStorage = _context.getExternalFilesDir(FILE_FOLDER);
        File personFile = new File(externalStorage, FILE_NAME);
        try {
            FileInputStream fis = new FileInputStream(personFile);
            data = IOUtils.toString(fis);
            fis.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(data == null) {
            return new ArrayList<>();
        }

        JSONArray peopleJson = null;
        try {
            peopleJson = new JSONArray(data);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        if(peopleJson == null) {
            throw new IllegalStateException("Problem reading JSON.");
        }

        ArrayList<Person> people = null;
        try {
            people = PersonConversionUtil.getPeopleListFromJSON(peopleJson);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        if(people == null) {
            throw new IllegalStateException("Problem converting JSON to people.");
        }

        return people;
    }

    public static void deletePersonFile(Context _context) {
        File externalStorage = _context.getExternalFilesDir(FILE_FOLDER);
        File personFile = new File(externalStorage, FILE_NAME);
        personFile.delete();
    }

}
