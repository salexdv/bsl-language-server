Процедура Тест1(Знач Парам1)
    Парам1 = 10; // ошибка
КонецПроцедуры

Процедура Тест2(Парам21, Знач Парам22)
    Парам21 = 10; // не ошибка
КонецПроцедуры

Процедура Тест3(Знач Парам31 = 1, Знач Парам32 = 2 )
    Парам31 = 3; // ошибка
КонецПроцедуры

Процедура Тест4(Знач Парам41)
    Парам41 = Парам41; // не ошибка
КонецПроцедуры

Процедура Тест5(Знач Парам51)
    Парам51 = Метод(Парам51); // не ошибка
КонецПроцедуры

Процедура Тест6(Знач Парам61)
    Парам61 = Парам61;
    Парам61 = 12; // ошибка
КонецПроцедуры

Процедура Тест7(Знач Парам71)
    Парам71 = Парам71;
    Парам71 = Парам71;
    Парам71 = Парам71;
    Парам71 = 12; // ошибка
КонецПроцедуры

Процедура Тест8(Знач Парам81) // для покрытия
    ЛокальнаяПеременная = 10;
КонецПроцедуры

Процедура Тест9(Знач Парам91)
    Парам91 = 12; // ошибка
    Значение = Парам91;
КонецПроцедуры

Процедура Тест10(Знач Парам101)
    Значение = Парам101; // не ошибка
КонецПроцедуры

Процедура Тест11(Знач Парам111)
    Парам111 = Парам111.Реквизит; // не ошибка
КонецПроцедуры

Процедура Тест12(Знач Парам121)
    Парам121 = Парам121 + Выражение; // не ошибка
КонецПроцедуры

Процедура Тест13(Знач Парам131)
    Парам131 = 1 + Парам131; // не ошибка
    Возврат Парам131;
КонецПроцедуры
