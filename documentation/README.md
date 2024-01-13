# Процедура запуска автотестов

### Необходимые приложения для запуска

- **IntelliJ IDEA Community Edition 2023.1**
- **Git и Github**
- **Docker**
- **Google Chrome**


1. Склонировать репозиторий

<code>git clone https://github.com/AnnaKotovskaya/Diplom.git</code>

2. Открыть проект в IntelliJ IDEA

3. Запустить контейнеры в Docker с помощью команды в терминале

   <code>docker-compose up</code>

4. В новой вкладке терминала ввести следующую команду:

   4.1. Для базы данных mysql

   <code>java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar</code>

   4.2. Для базы данных postgres

   <code>java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar</code>

5. Проверить доступность приложения в браузере по адресу:

       http://localhost:8080/

6. Запустить автотесты командой в терминале:

   6.1. Для базы данных mysql

   <code> ./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"     </code>

   6.2. Для базы данных postgres

   <code>./gradlew clean test "-D db.url=jdbc:postgresql://localhost:5432/app"</code>

7. Сформировать отчет Allure, выполнив команду в терминале IDEA:

   <code> ./gradlew allureServe</code>

8. Для перехода между базами данных (mysql, postgres), в окне терминала следует использовать команду Ctrl + C, чтобы
   останавливать подключение.

