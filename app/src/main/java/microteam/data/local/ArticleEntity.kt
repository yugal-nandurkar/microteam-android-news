
package microteam.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.ARTICLE_TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val link: String,
    val author: String,
    val pubDate: Long,
    val image: String,
    val feedTitle: String,
)
