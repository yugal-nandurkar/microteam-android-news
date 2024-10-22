
package microteam.data.remote

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

private const val TAG = "FeedParser"

class FeedParser {
    private val pullParserFactory = XmlPullParserFactory.newInstance()
    private val parser = pullParserFactory.newPullParser()

    fun parse(xml: String): List<ArticleFeed> {
        parser.setInput(xml.byteInputStream(), null)

        val articlesFeed = mutableListOf<ArticleFeed>()

        var feedTitle = ""

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "title") {
                feedTitle = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG && parser.name == "item") {
                val feedItem = readFeedItem(parser)
                val articleFeed =
                    ArticleFeed(
                        feedItem = feedItem,
                        feedTitle = feedTitle,
                    )
                articlesFeed.add(articleFeed)
            }
            parser.next()
        }

        return articlesFeed
    }

    private fun readFeedItem(parser: XmlPullParser): FeedItem {
        var title = ""
        var link = ""
        var author = ""
        var pubDate = ""
        var image = ""

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "title") {
                title = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG && parser.name == "link") {
                link = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG && parser.name == "dc:creator") {
                author = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG && parser.name == "pubDate") {
                pubDate = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG && parser.name == "cover_image") {
                image = readText(parser)
            } else if (parser.eventType == XmlPullParser.START_TAG) {
                skipTag(parser)
            }
        }

        return FeedItem(title, link, author, pubDate, image)
    }

    private fun readText(parser: XmlPullParser): String {
        var text = ""
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType == XmlPullParser.TEXT) {
                text = parser.text
            }
        }
        return text
    }

    @Suppress("ControlFlowWithEmptyBody")
    private fun skipTag(parser: XmlPullParser) {
        while (parser.next() != XmlPullParser.END_TAG) {
        }
    }
}
