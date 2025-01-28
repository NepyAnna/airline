# airline
Опис проекту:
Метою проекту є розробка системи управління авіалініями за допомогою Spring, Spring Boot та Spring Security. Ця система забезпечуватиме повне управління користувачами(user), рейсами(flights/routs), бронюваннями(bookings) та напрямками(airports/destinations), включаючи розширені функції, такі як безпечна аутентифікація за допомогою Basic Auth або JWT. Система не повинна дозволяти вибір рейсів без доступних місць або тих, що перевищили крайній термін. Проект реалізується на Java 21, Maven та MySQL або PostgreSQL.
### Функціональні вимоги:
#### Управління users:
- Реєстрація, автентифікація та керування ролями (ROLE_ADMIN та ROLE_USER).
- Генерація та перевірка токенів JWT або використання Cookies для безпечних сесій у разі використання Basic Auth.

#### Управління flights:
+++Автоматичне створення рейсів у базі даних під час компіляції (за допомогою файлу `.sql`).
+++Зміна статусу рейсу на "недоступний", коли закінчаться місця або сплине дата.
#### Пошукова система : 
++++Query за кількома параметрами - Пошук за вказаними аеропортами вильоту та прибуття, датою та кількістю місць для бронювання.
#### Управління бронюваннями:
++Дозволити бронювання лише за умови існування вибраного маршруту та наявності місць.
++ Перевірка доступності перед підтвердженням бронювання.
++- Блокування місць на 15 хвилин під час оформлення бронювання.
#### Управління для адміністратора (ROLE_ADMIN):
+++ CRUD для аеропортів.
+++ CRUD для маршрутів рейсів.
+++Перегляд зведеного списку бронювань клієнтів.
?+- Отримання історії бронювань кожного користувача./може пошук Bookings за id_user або достатньо що юзер зберігає свої букінги?
 #### Управління для клієнта (ROLE_USER):
- Реєстрація.
+-Завантаження зображення профілю (або використання зображення за замовчуванням, якщо воно відсутнє).
- Логін.
+- Отримання списку бронювань із деталями рейсів.
+- Бронювання лише після авторизації.
+- Налаштування персоналізованої обробки винятків.
### Нефункціональні вимоги:
- **Безпека**: Використання Spring Security з Basic Auth або JWT для захисту API.
- **Продуктивність**: Автоматична зміна статусів рейсів та перевірка бронювань для підтримання ефективності системи.
- **Доступність**: Проведення тестування для забезпечення стабільності системи.
### Додаткові можливості:
- Розгортання у Docker і завантаження зображення в Docker Hub.
- Використання GitHub Actions для автоматизації CI (збирання та запуск тестів).
- Автоматизація тестів із Postman.

### Очікувані результати:
1. Вихідний код бекенда в Spring Boot (посилання на GitHub).
2. Колекція Postman з усіма кінцевими точками та проведеними тестами (для показу під час перевірки).
3. Документація API (повний Readme, діаграми, список кінцевих точок тощо).
4. Презентація процесу розробки API.
5. Docker-образ програми, завантажений у Docker Hub (додатково).