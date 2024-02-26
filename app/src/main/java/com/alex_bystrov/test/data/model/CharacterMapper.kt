package com.alex_bystrov.test.data.model

import com.alex_bystrov.test.domain.model.Character

class CharacterMapper {
    fun mapToCharacterModel(character: Character): CharacterModel =
        CharacterModel(
            id = character.id,
            imageCharacter = character.image,
            name = character.name,
            status = character.status,
            species = character.species
        )

    fun mapToDomainModel(characterModel: CharacterModel): Character =
        Character(
            id = characterModel.id,
            image = characterModel.imageCharacter,
            name = characterModel.name,
            status = characterModel.status,
            species = characterModel.species
        )

    fun mapListCharacterModelToDomain(characters: List<CharacterModel>): List<Character> {
        return characters.map { mapToDomainModel(it) }
    }
}