package com.fullsail.android.unittestingstarter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;


import com.fullsail.android.unittestingstarter.object.Person;
import com.fullsail.android.unittestingstarter.util.PersonStorageUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonStorageTests {

    private static final String MOCK_FILE_LOCATION = "mockData.dat";

    private void clearMockStorage(){
        Context realContext = InstrumentationRegistry.getTargetContext();
        Context mockContext = Mockito.mock(Context.class);
        Mockito.when(mockContext.deleteFile(Mockito.anyString())).thenReturn(realContext.deleteFile(MOCK_FILE_LOCATION));
        PersonStorageUtil.deletePersonFile(mockContext);
    }

    @Test
    public void savePerson() throws IOException {
        Context mockContext = Mockito.mock(Context.class);
        Person katie = new Person("Katie", "Hrubiec", "6035559999", 27);
        Context realContext = InstrumentationRegistry.getTargetContext();
        Mockito.when(mockContext.getExternalFilesDir(Mockito.anyString())).thenReturn(realContext.getExternalFilesDir("mockjson"));
        PersonStorageUtil.savePerson(mockContext, katie);
        assertTrue(PersonStorageUtil.loadPeople(mockContext).contains(katie));
    }

    @Before
    public void setup() {
        clearMockStorage();
        Context realContext = InstrumentationRegistry.getTargetContext();
        File mockStorage = new File(realContext.getFilesDir(), MOCK_FILE_LOCATION);
        try {
            mockStorage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanup(){
        clearMockStorage();
    }

    @Test
    public void deletePerson(){
        Context mockContext = Mockito.mock(Context.class);
        Context realContext = InstrumentationRegistry.getTargetContext();
        Person katie = new Person("Katie", "Hrubiec", "6035559999", 27);
        Mockito.when(mockContext.getExternalFilesDir(Mockito.anyString())).thenReturn(realContext.getExternalFilesDir("mockjson"));
        PersonStorageUtil.savePerson(mockContext, katie);
        ArrayList<Person> people = PersonStorageUtil.loadPeople(realContext);
        PersonStorageUtil.deletePerson(mockContext,katie);
        people = PersonStorageUtil.loadPeople(mockContext);
        assertFalse(people.contains(katie));
    }

    @Test
    public void deletePersonFile(){
        Context mockContext = Mockito.mock(Context.class);
        Context realContext = InstrumentationRegistry.getTargetContext();
        Person katie = new Person("Katie", "Hrubiec", "6035559999", 27);
        Mockito.when(mockContext.getExternalFilesDir(Mockito.anyString())).thenReturn(realContext.getExternalFilesDir("mockjson"));
        PersonStorageUtil.savePerson(mockContext, katie);
        PersonStorageUtil.deletePersonFile(mockContext);
        assertTrue(PersonStorageUtil.loadPeople(mockContext).isEmpty());
    }

}
