# Хранение ip-адресов в коде (UsingHardcodeNetworkAddress)

|     Тип      |    Поддерживаются<br>языки    |  Важность   |    Включена<br>по умолчанию    |    Время на<br>исправление (мин)    |    Теги    |
|:------------:|:-----------------------------:|:-----------:|:------------------------------:|:-----------------------------------:|:----------:|
| `Уязвимость` |         `BSL`<br>`OS`         | `Критичный` |              `Да`              |                `15`                 | `standard` |

## Параметры


|               Имя               |   Тип    |                                   Описание                                    |                            Значение<br>по умолчанию                             |
|:-------------------------------:|:--------:|:-----------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|
|     `searchWordsExclusion`      | `Строка` |    `Ключевые слова поиска для исключения выражений при поиске IP адресов`     | `Верси|Version|ЗапуститьПриложение|RunApp|Пространств|Namespace|Драйвер|Driver` |
| `searchPopularVersionExclusion` | `Строка` | `Регулярное выражение для исключения популярных версий при поиске IP адресов` |                              `^(1|2|3|8\.3|11)\.`                               |
<!-- Блоки выше заполняются автоматически, не трогать -->
## Описание диагностики
<!-- Описание диагностики заполняется вручную. Необходимо понятным языком описать смысл и схему работу -->

Запрещено хранить в коде:

* Сетевые адреса (ip6, ip4)

Есть как минимум несколько способов правильного хранения такой информации:

* Хранение в константе.
* Хранение в регистре сведений.
* Хранение в отдельном модулей, в котором отключена проверка правила (не рекомендуется).
* Хранение в справочнике, узле плана обмена и т.д..

## Примеры
<!-- В данном разделе приводятся примеры, на которые диагностика срабатывает, а также можно привести пример, как можно исправить ситуацию -->

Неправильно:
```bsl
СетевойАдрес = "192.168.0.1";
```

Правильно:
```bsl
СетевойАдрес = МойМодульПовтИсп.СетевойАдресСервера();
```

## Сниппеты

<!-- Блоки ниже заполняются автоматически, не трогать -->
### Экранирование кода

```bsl
// BSLLS:UsingHardcodeNetworkAddress-off
// BSLLS:UsingHardcodeNetworkAddress-on
```

### Параметр конфигурационного файла

```json
"UsingHardcodeNetworkAddress": {
    "searchWordsExclusion": "Верси|Version|ЗапуститьПриложение|RunApp|Пространств|Namespace|Драйвер|Driver",
    "searchPopularVersionExclusion": "^(1|2|3|8\\.3|11)\\."
}
```
