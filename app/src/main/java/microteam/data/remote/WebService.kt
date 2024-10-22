
package microteam.data.remote

interface WebService {
    suspend fun getXMlString(url: String): String
}
