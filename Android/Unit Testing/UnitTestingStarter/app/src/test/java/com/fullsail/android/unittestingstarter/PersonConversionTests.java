package com.fullsail.android.unittestingstarter;

import com.fullsail.android.unittestingstarter.object.Person;
import com.fullsail.android.unittestingstarter.util.PersonConversionUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PersonConversionTests {

    @Test
    public void getPeopleJSONFromList() throws JSONException {
        ArrayList<Person> people = new ArrayList<>();
        JSONArray peopleJson = new JSONArray();

        Person katie = new Person("Katie", "Hrubiec", "6035559999", 27);
        people.add(katie);
        peopleJson.put(katie.getPersonAsJSON());

        assertEquals(PersonConversionUtil.getPeopleJSONFromList(people).toString(), peopleJson.toString());


        Person andrew = new Person("Andrew", "Rogers", "6035558888", 35);
        people.add(andrew);
        peopleJson.put(andrew.getPersonAsJSON());
        Person tucker = new Person("Tucker", "Rogers", "6035557777", 7);
        people.add(tucker);
        peopleJson.put(tucker.getPersonAsJSON());

        assertEquals(PersonConversionUtil.getPeopleJSONFromList(people).toString(), peopleJson.toString());
    }

    @Test
    public void getPeopleListFromJSON() throws JSONException {
        ArrayList<Person> people = new ArrayList<>();
        JSONArray peopleJson = new JSONArray();

        Person katie = new Person("Katie", "Hrubiec", "6035559999", 27);
        people.add(katie);
        peopleJson.put(katie.getPersonAsJSON());

        assertEquals(PersonConversionUtil.getPeopleListFromJSON(peopleJson).toString(), people.toString());

        Person andrew = new Person("Andrew", "Rogers", "6035558888", 35);
        people.add(andrew);
        peopleJson.put(andrew.getPersonAsJSON());
        Person tucker = new Person("Tucker", "Rogers", "6035557777", 7);
        people.add(tucker);
        peopleJson.put(tucker.getPersonAsJSON());

        assertEquals(PersonConversionUtil.getPeopleListFromJSON(peopleJson).toString(), people.toString());
    }
}
