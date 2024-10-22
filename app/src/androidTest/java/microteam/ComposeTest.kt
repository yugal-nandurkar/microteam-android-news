
package microteam

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import microteam.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test

class ComposeTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavigationNames_areValid() {
        var text = composeTestRule.activity.getString(R.string.home)
        composeTestRule.onNodeWithText(text).assertExists()

        text = composeTestRule.activity.getString(R.string.unread_articles)
        composeTestRule.onNodeWithText(text).assertExists()

        text = composeTestRule.activity.getString(R.string.bookmarks)
        composeTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun clickBookmarks_clearBookmarks_showsNoArticles() {
        /* This test is not done yet, work in progress
        var text = composeTestRule.activity.getString(R.string.bookmarks)
        composeTestRule.onNodeWithText(text).performClick()
         */
    }
}
