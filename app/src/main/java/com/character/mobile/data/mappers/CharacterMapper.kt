package com.character.mobile.data.mappers

import com.character.mobile.data.models.EpisodeDto
import com.character.mobile.data.models.InfoDto
import com.character.mobile.data.models.CharacterDto
import com.character.mobile.data.models.CharacterResponseDto
import com.character.mobile.data.models.OriginDto
import com.character.mobile.domain.models.Episode
import com.character.mobile.domain.models.Info
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.models.CharacterResponse
import com.character.mobile.domain.models.Origin

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        species = species,
        image = image,
        status = status,
        type = type,
        gender = gender,
        url = url,
        created = created,
        episode = episode,
        location = location?.toDomain(),
        origin = origin?.toDomain(),
    )
}

fun InfoDto.toDomain(): Info {
    return Info(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

fun CharacterResponseDto.toDomain(): CharacterResponse {
    return CharacterResponse(
        info = info.toDomain(),
        results = results.map { dto -> dto.toDomain() }
    )
}

fun OriginDto.toDomain(): Origin {
    return Origin(name = name, url = url)
}

fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters,
        url = url,
        created = created

    )

}