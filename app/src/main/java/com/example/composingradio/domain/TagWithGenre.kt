package com.example.composingradio.domain


sealed interface TagWithGenre {
    data class Genre (
        val genre : String,
        var isOpen : Boolean = true,
        val tags : List<Tag>
    ) : TagWithGenre


    data class Tag (
        val tag : String,
        val count : Int = 0
    ) : TagWithGenre
}
