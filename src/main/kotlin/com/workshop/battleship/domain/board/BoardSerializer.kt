package com.workshop.battleship.domain.board

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Board::class)
object BoardSerializer : KSerializer<Board> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Board") {
        element<String>("id")
        element<String>("plays")
        element<String?>("player")
    }

    override fun serialize(encoder: Encoder, value: Board) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.id.toString())
            encodeStringElement(descriptor, 1, value.plays)
            encodeStringElement(descriptor, 2, value.player)
        }
    }

    override fun deserialize(decoder: Decoder): Board {
        return decoder.decodeStructure(descriptor) {
            var id: UUID? = null
            var plays: String? = null
            var player: String? = null

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop

                    0 -> id = UUID.fromString(decodeStringElement(descriptor, 0))
                    1 -> plays = decodeStringElement(descriptor, 1)
                    2 -> player = decodeStringElement(descriptor, 2)

                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            Board(
                requireNotNull(id),
                requireNotNull(plays),
                requireNotNull(player),
            )
        }
    }
}
