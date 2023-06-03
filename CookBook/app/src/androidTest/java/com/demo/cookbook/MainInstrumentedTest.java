package com.demo.cookbook;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import com.demo.cookbook.ui.favor.FavorRecyclerAdapter;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainInstrumentedTest {
  private static final String BASIC_PACKAGE = "com.demo.cookbook";

  @Rule
  public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = getInstrumentation().getTargetContext();
    assertEquals(BASIC_PACKAGE, appContext.getPackageName());
  }

  private static final int LAUNCH_TIMEOUT = 5000;

  private UiDevice mDevice;

  @Before
  public void startMainActivityFromHomeScreen() {
    // Initialize UiDevice instance
    mDevice = UiDevice.getInstance(getInstrumentation());

    // Start from the home screen
    mDevice.pressHome();

    // Wait for launcher
    final String launcherPackage = getLauncherPackageName();
    assertThat(launcherPackage, notNullValue());
    mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

    // Launch the blueprint app
    Context context = getApplicationContext();
    final Intent intent = context.getPackageManager()
            .getLaunchIntentForPackage(BASIC_PACKAGE);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
    context.startActivity(intent);

    // Wait for the app to appear
    mDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LAUNCH_TIMEOUT);


  }

  @Test
  public void changeFavorFragmentByBottomNavTab() {
    onView(withId(R.id.rb_favor)).perform(click());
    // Verify the test is displayed in the Ui
    UiObject2 toolBar = mDevice
            .wait(Until.findObject(By.res(BASIC_PACKAGE, "toolBar")), 500 /* wait 500ms */);
    assertThat(toolBar.getText(), is(equalTo(getApplicationContext().getString(R.string.title_favor))));
  }

  @Test
  public void changeHomeFragmentByBottomNavTab() {
    onView(withId(R.id.rb_home)).perform(click());
    // Verify the test is displayed in the Ui
    UiObject2 toolBar = mDevice
            .wait(Until.findObject(By.res(BASIC_PACKAGE, "toolBar")), 500 /* wait 500ms */);
    assertThat(toolBar.getText(), is(equalTo(getApplicationContext().getString(R.string.title_home))));
  }

  @Test
  public void changeShopFragmentByBottomNavTab() {
    onView(withId(R.id.rb_shop)).perform(click());
    // Verify the test is displayed in the Ui
    UiObject2 toolBar = mDevice
            .wait(Until.findObject(By.res(BASIC_PACKAGE, "toolBar")), 500 /* wait 500ms */);
    assertThat(toolBar.getText(), is(equalTo(getApplicationContext().getString(R.string.title_shop))));
  }

  @Test
  public void clickFavorItemOpenToDetailPage() {
    onView(withId(R.id.rb_favor)).perform(click());

    SystemClock.sleep(1500);

    onView(allOf(withId(R.id.recycler_view), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    UiObject2 toolBar = mDevice
            .wait(Until.findObject(By.res(BASIC_PACKAGE, "toolBar")), 500 /* wait 500ms */);
    assertThat(toolBar.getText(), is(equalTo(getApplicationContext().getString(R.string.title_detail))));
  }

  /**
   * Uses package manager to find the package name of the device launcher. Usually this package
   * is "com.android.launcher" but can be different at times. This is a generic solution which
   * works on all platforms.`
   */
  private String getLauncherPackageName() {
    // Create launcher Intent
    final Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);

    // Use PackageManager to get the launcher package name
    PackageManager pm = getApplicationContext().getPackageManager();
    ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
    return resolveInfo.activityInfo.packageName;
  }
}