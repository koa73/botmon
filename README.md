# botmon
Telegram Bot для доставки алерт сообщении с информационных систем и систем мониторинга подписчикам

---

Функции бота:

**/start** - инициация взаимодействия

**/list** - вывод списка зарегистрированных пользователей

**/help** - вывод списка команд

**@<номер телефона>  Текст** - отправка адресного сообщения
формат телефона: **+79xxxxxxxxx, +7 (9xx) xxx-xx-xx**

---

Регистрация пользователя :

1. Подключиться к боту, найдя его по поиску
2. Отправить _/start_
3. Ввести пароль
4. Отправить контактные данные ( нажать появившуюся снизу кнопку, после успешной авторизации по паролю )


---

Отправка сообщений через REST интерфейс

_http://<имя хоста>:8226/rest/send/?from=<Отправитель>&phone=<телефон получателя>&message=<Сообщение>&key=<ключ>_

Методы отправки: **GET, POST**

Формат отправителя: **text  max 15**

Формат сообщения: **text max 607**

Формат телефона: **89xxxxxxxxx** или  **89xxxxxxxxx,89xxxxxxxxx,89xxxxxxxxx**
 
 Ответ:
 
 200
 Ок
 
 или
 
 4xx, 5xx 
 
 например
 
 {"error":"User(s) [89225137185, 89264177182] not found.", "cause":"ru.rgs.botmon.service.bot.BotMonImpl"}