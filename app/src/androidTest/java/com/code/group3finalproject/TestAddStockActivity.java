package com.code.group3finalproject;

import android.os.Looper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4::class)
class TestAddStockActivity {

    @Rule
    public ActivityScenarioRule<AddStockActivity> activityRule = new ActivityScenarioRule(AddStockActivity.class);

    @Test
    public void testValidStockQuotes () {
        activityRule.getScenario().onActivity(activity -> {
            JSONArray stock = new AddStockActivity().new GetContacts(":","6U3CR4XTV8H80PTS").doBGWork();
        });
    }
}
