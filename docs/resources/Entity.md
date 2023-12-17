[http-get]: https://img.shields.io/badge/GET-505CDC

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

## List user ids</br>![http-get] /entities
Returns a list of all known entity ids.

## Get user</br>![http-get] /entities/[{entity.id}](#entity-object)
Returns a single [entity](#entity-object) object.
