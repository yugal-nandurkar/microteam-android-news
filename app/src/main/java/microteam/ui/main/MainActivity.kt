
package microteam.ui.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.launch
import microteam.data.repository.ArticlesRepositoryImpl
import microteam.data.repository.UserPreferencesRepositoryImpl
import microteam.domain.model.ArticlesUiState
import microteam.ui.main.viewmodel.ArticlesViewModel
import microteam.ui.main.viewmodel.ArticlesViewModelFactory
import microteam.ui.theme.AndroidNewsTheme

class MainActivity : ComponentActivity() {
    private val articlesRepository by lazy {
        ArticlesRepositoryImpl.getInstance(application)
    }

    private val userPrefsRepository by lazy {
        UserPreferencesRepositoryImpl.getInstance(application)
    }

    private val viewModel: ArticlesViewModel by viewModels {
        ArticlesViewModelFactory(articlesRepository, userPrefsRepository)
    }

    private val reviewManager by lazy {
        ReviewManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            AndroidNewsTheme(useSystemUIController = true) {
                MainScreen()
            }
        }

        showReviewDialog()
    }

    private fun setupSplashScreen() {
        var keepSplashScreenOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    keepSplashScreenOn =
                        uiState is ArticlesUiState.Loading ||
                        uiState is ArticlesUiState.Invalid
                }
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOn
        }
    }

    private fun showReviewDialog() {
        val packageInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                packageManager.getPackageInfo(packageName, 0)
            }

        val installTime = packageInfo.firstInstallTime
        val currentTime = System.currentTimeMillis()
        val durationInMillis = currentTime - installTime
        val durationInDays = durationInMillis / (1000 * 60 * 60 * 24)

        if (durationInDays < 7) return

        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                reviewManager.launchReviewFlow(this, reviewInfo)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainActivityPreview() {
    MainScreenPreview()
}
