package com.fullsail.android.unittestingstarter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.fullsail.android.unittestingstarter.util.PersonFormatUtil;
import com.fullsail.android.unittestingstarter.util.PreferenceUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceUtilTests {

    private static final String PREF_NAME_FORMAT = "com.fullsail.android.PREF_NAME_FORMAT";
    private static final String PREF_NUMBER_FORMAT = "com.fullsail.android.PREF_NUMBER_FORMAT";
    Context mockContext = Mockito.mock(Context.class);
    Context realContext = InstrumentationRegistry.getTargetContext();
    final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);


    @Test
    public void nameFormatFirstLast(){
        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPrefs);

        Mockito.when(sharedPrefs.getInt(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(realContext.getSharedPreferences(PREF_NAME_FORMAT, 0).getInt("test", 0));

        assertEquals(PreferenceUtil.getNameFormat(mockContext), PersonFormatUtil.FORMAT_FIRST_LAST);
    }

    @Test
    public void nameFormatLastFirst(){

        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPrefs);

        Mockito.when(sharedPrefs.getInt(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(realContext.getSharedPreferences(PREF_NAME_FORMAT, 0).getInt("test", 1));

        assertEquals(PreferenceUtil.getNameFormat(mockContext),PersonFormatUtil.FORMAT_LAST_FIRST);
    }

    @Test
    public void formatPhoneAllDashes(){

        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPrefs);

        Mockito.when(sharedPrefs.getInt(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(realContext.getSharedPreferences(PREF_NUMBER_FORMAT, 0).getInt("test", 0));

        assertEquals(PreferenceUtil.getPhoneFormat(mockContext),PersonFormatUtil.FORMAT_ALL_DASHES);
    }

    @Test
    public void formatPhoneParens(){

        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPrefs);

        Mockito.when(sharedPrefs.getInt(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(realContext.getSharedPreferences(PREF_NUMBER_FORMAT, 0).getInt("test", 1));

        assertEquals(PreferenceUtil.getPhoneFormat(mockContext),PersonFormatUtil.FORMAT_WITH_PARENS);
    }

    @Test
    public void formatPhoneSpaces(){

        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPrefs);

        Mockito.when(sharedPrefs.getInt(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(realContext.getSharedPreferences(PREF_NUMBER_FORMAT, 0).getInt("test", 2));

        assertEquals(PreferenceUtil.getPhoneFormat(mockContext),PersonFormatUtil.FORMAT_WITH_SPACES);
    }

}
