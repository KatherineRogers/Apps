package com.fullsail.android.unittestingstarter.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Person {

    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_AGE = "age";

    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    private int mAge;

    public Person() {
        mFirstName = mLastName =
                mPhoneNumber = "";
        mAge = -1;
    }

    public Person(String _first, String _last, String _phone, int _age) {
        mFirstName = _first;
        mLastName = _last;
        mPhoneNumber = _phone;
        mAge = _age;
    }

    public Person(JSONObject _personJson) throws JSONException {
        mFirstName = _personJson.getString(KEY_FIRST_NAME);
        mLastName = _personJson.getString(KEY_LAST_NAME);
        mPhoneNumber = _personJson.getString(KEY_PHONE_NUMBER);
        mAge = _personJson.getInt(KEY_AGE);
    }

    public JSONObject getPersonAsJSON() throws JSONException {
        JSONObject person = new JSONObject();
        person.put(KEY_FIRST_NAME, mFirstName);
        person.put(KEY_LAST_NAME, mLastName);
        person.put(KEY_PHONE_NUMBER, mPhoneNumber);
        person.put(KEY_AGE, mAge);
        return person;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public int getAge() {
        return mAge;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person)obj;

        return Objects.equals(other.mFirstName, mFirstName) &&
                Objects.equals(other.mLastName, mLastName) &&
                Objects.equals(other.mPhoneNumber, mPhoneNumber) &&
                other.mAge == mAge;
    }

    @Override
    public String toString() {
        return mFirstName + " " + mLastName + " - " + mAge + ": " + mPhoneNumber;
    }
}
