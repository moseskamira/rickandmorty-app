package com.kamira.mortyverse.data.mappers

import com.kamira.mortyverse.data.models.EpisodeDto
import com.kamira.mortyverse.data.models.InfoDto
import com.kamira.mortyverse.data.models.CharacterDto
import com.kamira.mortyverse.data.models.CharacterResponseDto
import com.kamira.mortyverse.data.models.OriginDto
import com.kamira.mortyverse.domain.models.Episode
import com.kamira.mortyverse.domain.models.Info
import com.kamira.mortyverse.domain.models.Character
import com.kamira.mortyverse.domain.models.CharacterResponse
import com.kamira.mortyverse.domain.models.Origin

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