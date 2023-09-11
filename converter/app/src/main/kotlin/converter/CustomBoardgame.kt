package converter

data class CustomBoardgame(
    val objectId: Int,
    val yearPublished: Int,
    val minPlayers: Int,
    val maxPlayers: Int,
    val playingTime: Int,
    val minPlayTime: Int,
    val maxPlayTime: Int,
    val age: Int,
    val name: String,
    val description: String,
    val thumbnailImageLink: String?,
    val publisher: String?,
    val version: String?,
    val category: String?,
    val families: List<String>,
    val gameMechanics: String?,
    val bestPlayedWith: String,
    val boardGameRank: Int
)
