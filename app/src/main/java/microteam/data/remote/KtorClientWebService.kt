
package microteam.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse

class KtorClientWebService : WebService {
    override suspend fun getXMlString(url: String): String {
        val client = HttpClient()
        /*
        This throws the following exception when it run on API 21:
        java.util.concurrent.ExecutionException: java.lang.NoClassDefFoundError: io.ktor.util.collections.ConcurrentMap$$ExternalSyntheticLambda0

        Use OkHttpWebService instead
         */
        val response: HttpResponse = client.request(url)
        client.close()
        return response.body()
    }
}
