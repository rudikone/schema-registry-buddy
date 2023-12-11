# SCHEMA REGISTRY BUDDY PLUGIN

GRADLE-плагин для регистрации схем под сабджектом(топиком) в schema-registry.

Использует [API Schema registry](https://docs.confluent.io/platform/current/schema-registry/develop/api.html).

## Use case

Используешь в своем севрисе Kafka + Avro? Поднимаешь инфрструктуру (Kafka + Schema Registry) для тестирования на локальной машине? Тебе нужно протестировать consumer? - этот плагин избавит от 
ручной работы по регистрации необходимой consumer'у Avro схемы.
 
## Использование

Склонируй проект, выполни:

```
gradle publishToMavenLocal
```

В целевом проекте:

settings.gradle.kts:
```
pluginManagement {
    repositories {
        mavenLocal()
    }
}
```

build.gradle.kts:
```
plugins {
    id("ru.rudikov.schema-registry-buddy") version "0.1.0"
}

schemaRegistryBuddyProps {
    schemaRegistryUrl = "some_url"
    searchPaths.add("search_path")
    subjectToSchema["some_topic"] = "some_file_name"
}
```

Выполни:
```
gradle registerSchemas
```

Плагин найдет в search_path файл с именем some_file_name.avcs (в subjectToSchema имя файла указывается без расширения), затем выполнит [POST-запрос](https://docs.confluent.io/platform/current/schema-registry/develop/api.html#post--subjects-(string-%20subject)-versions) для регистрации схемы. В случае успеха в консоль будут выведены id успешно зарегистрированных схем:
```
{"id":1}
```

## Параметры schemaRegistryBuddyProps

| Параметр           | Описание                                                             | Значение по умолчанию |
|--------------------|----------------------------------------------------------------------|---------------------|
| schemaRegistryUrl  | Адрес schema registry (String)                                       | "http://localhost:10081" |
| searchPaths        | Список директорий для поиска файлов с расширением .avcs (MutableSet) | project.buildDir    |
| subjectToSchema    | Имя топика и схемы под ним (MutableMap)                              | mutableMapOf()      |
