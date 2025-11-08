package com.tving.feat.contentdetail.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.contentdetail.ContentDetailRoute

private val gson = Gson()

internal class ContentDetailArgsImpl(arguments: Bundle?) {
    val contentType: String = arguments?.getString("contentType") ?: ""
    private val videoJson: String? = arguments?.getString("videoJson")
    private val imageJson: String? = arguments?.getString("imageJson")
    
    val video: VideoSearch? = videoJson?.let { 
        try {
            gson.fromJson(Uri.decode(it), VideoSearch::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    val image: ImageSearch? = imageJson?.let {
        try {
            gson.fromJson(Uri.decode(it), ImageSearch::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

fun NavController.navigateToContentDetailWithVideo(
    video: VideoSearch,
    navOptions: NavOptions? = null
) {
    val videoJson = Uri.encode(gson.toJson(video))
    navigate(
        route = "contentdetail/${NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO}?videoJson=$videoJson",
        navOptions = navOptions
    )
}

fun NavController.navigateToContentDetailWithImage(
    image: ImageSearch,
    navOptions: NavOptions? = null
) {
    val imageJson = Uri.encode(gson.toJson(image))
    navigate(
        route = "contentdetail/${NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE}?imageJson=$imageJson",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentDetailNavigation() {
    composable(
        route = "contentdetail/{contentType}?videoJson={videoJson}&imageJson={imageJson}",
        arguments = listOf(
            navArgument("contentType") { 
                type = NavType.StringType 
            },
            navArgument("videoJson") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("imageJson") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { backStackEntry ->
        val args = ContentDetailArgsImpl(backStackEntry.arguments)

        when (args.contentType) {
            NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO -> {
                args.video?.let { video ->
                    ContentDetailRoute(
                        video = video,
                        image = null
                    )
                }
            }
            NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE -> {
                args.image?.let { image ->
                    ContentDetailRoute(
                        video = null,
                        image = image
                    )
                }
            }
        }
    }
}