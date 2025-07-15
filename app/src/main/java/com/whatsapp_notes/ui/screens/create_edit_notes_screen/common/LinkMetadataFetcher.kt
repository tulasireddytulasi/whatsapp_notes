package com.whatsapp_notes.ui.screens.create_edit_notes_screen.common

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

object LinkMetadataFetcher {

    private const val TAG = "LinkMetadataFetcher"

    data class LinkMetadata(
        val url: String,
        val title: String?,
        val description: String?,
        val imageUrl: String?
    )

    /**
     * Extracts all URLs from a given string and then fetches metadata (title, description, image)
     * for each link, stopping at the first one for which metadata is successfully retrieved.
     *
     * @param text The string to search for URLs.
     * @return LinkMetadata object if a URL is found and metadata can be fetched, null otherwise.
     */
    suspend fun fetchFirstAvailableLinkMetadata(text: String): LinkMetadata? = withContext(Dispatchers.IO) {
        val urlRegex = "(http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+)"
        val pattern = Regex(urlRegex)
        val matchResults = pattern.findAll(text)

        // Iterate through all found links
        for (matchResult in matchResults) {
            val currentLink = matchResult.value
            try {
                val document = Jsoup.connect(currentLink).get()

                val title = document.select("meta[property=og:title]").first()?.attr("content")
                    ?: document.select("title").first()?.text()

                val description = document.select("meta[property=og:description]").first()?.attr("content")
                    ?: document.select("meta[name=description]").first()?.attr("content")

                val imageUrl = document.select("meta[property=og:image]").first()?.attr("content")
                    ?: document.select("link[rel=apple-touch-icon]").first()?.attr("href")
                    ?: document.select("link[rel=icon]").first()?.attr("href")

                // Check if at least title or description is present to consider it "having data"
                if (!title.isNullOrBlank() || !description.isNullOrBlank()) {
                    Log.d(TAG, "Successfully fetched metadata for: $currentLink, \n$imageUrl")
                    return@withContext LinkMetadata(currentLink, title, description, imageUrl)
                } else {
                    Log.d(TAG, "Link '$currentLink' found, but no significant metadata (title/description) available. Trying next...")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching metadata for link: $currentLink. Trying next...", e)
            }
        }

        Log.d(TAG, "No link found in the provided text or no link had sufficient metadata.")
        return@withContext null // No link yielded sufficient metadata
    }
}