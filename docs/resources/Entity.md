[http-get]: https://img.shields.io/badge/GET-505CDC
[http-put]: https://img.shields.io/badge/PUT-AC5A1F
[http-delete]: https://img.shields.io/badge/DELETE-A12828

# Entity resource

## Entity object

##### Entity Structure

| Field     | Type                                                 | Description                       |
|-----------|------------------------------------------------------|-----------------------------------|
| id        | turtle                                               | The entity's unique id            |
| overrides | array of [permission](#permission-structure) objects | The entity's permission overrides |

##### Permission Structure

| Field | Type   | Description                                              |
|-------|--------|----------------------------------------------------------|
| name  | string | Unique permission identifier                             |
| value | bool   | True, if the entity has this permission or false if not. |

## List entity ids</br>![http-get] /entities
Returns a list of all known entity ids.

## Get entity</br>![http-get] /entities/[{entity.id}](#entity-object)
Returns a single [entity](#entity-object) object.
If no entity with the specified id exists this will return `204 No Content`.

## Set permission override</br>![http-put] /entities/[{entity.id}](#entity-object)/[{permission.name}](#permission-structure)
Creates or updates a permission override of an entity.
Returns `204 No Content` on success.

| Parameter | Type | Description               |
|-----------|------|---------------------------|
| value     | bool | Permission override value |

## Remove permission override</br>![http-delete] /entities/[{entity.id}](#entity-object)/[{permission.name}](#permission-structure)
Removes a permission override from an entity.
Returns `204 No Content` on success.
