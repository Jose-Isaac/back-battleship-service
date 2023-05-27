package com.workshop.battleship.domain.board

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Coordinate::class)
object CoordinateSerializer : KSerializer<Coordinate> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Coordinate") {
        element<Int>("axisX")
        element<Int>("axisY")
        element<String?>("occupiedByPlayer")
        element<Boolean>("isOccupied")
        element<Boolean>("gotHit")
        element<String?>("wasHitBy", isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Coordinate) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.axisX)
            encodeIntElement(descriptor, 1, value.axisY)
            encodeNullableSerializableElement(descriptor, 2, String.serializer(), value.occupiedByPlayer)
            encodeBooleanElement(descriptor, 3, value.isOccupied)
            encodeBooleanElement(descriptor, 4, value.gotHit)
            encodeNullableSerializableElement(descriptor, 5, String.serializer(), value.wasHitBy)
        }
    }

    override fun deserialize(decoder: Decoder): Coordinate {
        return decoder.decodeStructure(descriptor) {
            var axisX: Int? = null
            var axisY: Int? = null
            var occupiedByPlayer: String? = null
            var isOccupied: Boolean? = null
            var gotHit: Boolean? = null
            var wasHitBy: String? = null

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop

                    0 -> axisX = decodeIntElement(descriptor, 0)
                    1 -> axisY = decodeIntElement(descriptor, 1)
                    2 -> occupiedByPlayer = decodeNullableSerializableElement(descriptor, 2, String.serializer().nullable)
                    3 -> isOccupied = decodeBooleanElement(descriptor, 3)
                    4 -> gotHit = decodeBooleanElement(descriptor, 4)
                    5 -> wasHitBy = decodeNullableSerializableElement(descriptor, 5, String.serializer().nullable)

                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            Coordinate(
                requireNotNull(axisX),
                requireNotNull(axisY),
                occupiedByPlayer,
                requireNotNull(isOccupied),
                requireNotNull(gotHit),
                wasHitBy,
            )
        }
    }
}
