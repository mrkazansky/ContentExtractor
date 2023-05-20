package com.mrkaz.data.model

data class ExtractedCommentModel(val mentions: List<String>, val links: List<LinkContent>)
data class LinkContent(val url: String, val title: String)