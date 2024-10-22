
package microteam.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM ${DatabaseConstants.ARTICLE_TABLE_NAME} ORDER by pubDate DESC")
    fun selectAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.ARTICLE_TABLE_NAME} WHERE title LIKE :query ORDER by pubDate DESC")
    fun selectAllArticlesByTitle(query: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.ARTICLE_TABLE_NAME} WHERE id = :id")
    fun selectArticleById(id: String): Flow<ArticleEntity?>

    @Query("SELECT * FROM ${DatabaseConstants.ARTICLE_TABLE_NAME} WHERE id= :id")
    fun getArticleById(id: String): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleEntity)

    @Update
    fun updateArticle(article: ArticleEntity)

    @Query("DELETE FROM ${DatabaseConstants.ARTICLE_TABLE_NAME}")
    fun deleteAllArticles()
}
