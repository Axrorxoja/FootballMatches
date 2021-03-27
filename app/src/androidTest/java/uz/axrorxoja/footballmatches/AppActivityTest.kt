package uz.axrorxoja.footballmatches

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(AppActivity::class.java)

    @Test
    fun appActivityTest2() {
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.image_view))
            .check(matches(isDisplayed()))
        onView(withText("Name:"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_name))
            .check(matches(isDisplayed()))
        onView(withText("ShortName:"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_short_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.label_address))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_address))
            .check(matches(isDisplayed()))
        onView(withId(R.id.label_phone))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_phone))
            .check(matches(isDisplayed()))
        onView(withId(R.id.label_website))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_website))
            .check(matches(isDisplayed()))
        onView(withId(R.id.label_email))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_email))
            .check(matches(isDisplayed()))
    }
}
