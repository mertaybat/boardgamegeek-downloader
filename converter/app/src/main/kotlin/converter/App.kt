package converter

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import com.fasterxml.jackson.databind.ObjectMapper
import generated.Boardgame
import generated.Boardgames
import java.io.File
import java.io.FileWriter
import javax.xml.bind.JAXBContext

fun main() {
    val context = JAXBContext.newInstance(Boardgames::class.java)
    val unmarshaller = context.createUnmarshaller()
    val objectMapper = ObjectMapper()
    objectMapper.setSerializationInclusion(NON_EMPTY)
    val listOfBoardgames: MutableList<Boardgame> = mutableListOf()
    val listOfCustomResults: MutableList<CustomBoardgame> = mutableListOf()
    File("./").listFiles()?.filter { it.isFile }?.filter { it.extension == "xml" }?.forEach {
        val boardgames = unmarshaller.unmarshal(it.inputStream()) as Boardgames
        boardgames.boardgame.forEach {
            listOfBoardgames.add(it)
            listOfCustomResults.add(it.toInternal())
        }
    }
    val writer = FileWriter("./boardgames.json")
    writer.write(objectMapper.writeValueAsString(listOfBoardgames))
    writer.flush()
    writer.close()
    val writer2 = FileWriter("./boardgames-custom.json")
    writer2.write(objectMapper.writeValueAsString(listOfCustomResults))
    writer2.flush()
    writer2.close()
}

private fun Boardgame.toInternal(): CustomBoardgame {
    return CustomBoardgame(
        objectId = this.objectid,
        yearPublished = this.yearpublished.toInt(),
        minPlayers = this.minplayers.toInt(),
        maxPlayers = this.maxplayers.toInt(),
        playingTime = this.playingtime.toInt(),
        minPlayTime = this.minplaytime.toInt(),
        maxPlayTime = this.maxplaytime.toInt(),
        age = this.age.toInt(),
        name = this.name.singleOrNull { it.primary == "true" }?.value ?: this.name.first().value,
        description = this.description,
        thumbnailImageLink = this.thumbnail,
        publisher = this.boardgamepublisher?.value,
        version = this.boardgameversion?.value,
        category = this.boardgamecategory?.value,
        families = this.boardgamefamily?.mapNotNull { it.value } ?: emptyList(),
        gameMechanics = this.boardgamemechanic?.value,
        bestPlayedWith = this.poll.single { it.name == "suggested_numplayers"}.results.sortedBy {
            it.result.single { it.valueAttribute == "Best" }.numvotes.toInt()
        }.last().numplayers,
        boardGameRank = this.statistics.ratings.ranks.rank.single { it.name == "boardgame" }.valueAttribute.toInt()
    )
}
