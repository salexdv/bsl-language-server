# Использование устаревших глобальных методов платформы 8.3.17 (DeprecatedMethods8317)

|      Тип      |    Поддерживаются<br>языки    |     Важность     |    Включена<br>по умолчанию    |    Время на<br>исправление (мин)    |     Теги     |
|:-------------:|:-----------------------------:|:----------------:|:------------------------------:|:-----------------------------------:|:------------:|
| `Дефект кода` |             `BSL`             | `Информационный` |              `Да`              |                 `5`                 | `deprecated` |

<!-- Блоки выше заполняются автоматически, не трогать -->
## Описание диагностики
<!-- Описание диагностики заполняется вручную. Необходимо понятным языком описать смысл и схему работу -->
В платформе `8.3.17` было реализовано свойство глобального контекста `ОбработкаОшибок` и 
стандартная функция `Управление настройками обработки ошибок`, позволяющая настроить тексты ошибок.

Методы глобального контекста считаются устаревшими:

* `КраткоеПредставлениеОшибки()`
* `ПодробноеПредставлениеОшибки()` 
* `ПоказатьИнформациюОбОшибке()`

Необходимо использовать одноименные методы объекта `ОбработкаОшибок`.

## Источники
<!-- Необходимо указывать ссылки на все источники, из которых почерпнута информация для создания диагностики -->

* Источник: [Описание изменений платформы 8.3.17](https://dl03.1c.ru/content/Platform/8_3_17_1386/1cv8upd_8_3_17_1386.htm#27f2dc70-f0cf-11e9-8371-0050569f678a)

## Сниппеты

<!-- Блоки ниже заполняются автоматически, не трогать -->
### Экранирование кода

```bsl
// BSLLS:DeprecatedMethods8317-off
// BSLLS:DeprecatedMethods8317-on
```

### Параметр конфигурационного файла

```json
"DeprecatedMethods8317": false
```
