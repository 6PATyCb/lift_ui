# Учебный проект "Лифт"

Основная идея учебного проекта "Лифт" - разработка приложения, которое будет имитировать работу лифта в обычном многоквартирном доме. Проект можно разбить на модули и вести разработку сразу командой из нескольких человек. Ниже я накидал основные идеи проекта и технологии с помощью которых их можно попробовать реализовать. Мои идеи не являются последней инстанцией и их можно изменять/адаптировать под какие-то еще технологии, которые бы хотелось изучить в процессе работы над проектом. Если вы чувствуете в себе силы начать этот проект с нуля сделайте это, а если уверенности недостаточно, то вы можете взять мою реализацию с минимально реализованной логикой, ознакомьтесь с подходами и улучшайте ее своими идеями.

### Как запустить проект
При сборке проекта не забудьте активировать maven профиль "production", чтобы vaadin корректно скомпилировал свою фронтовую часть. После запуска проект доступен по ссылке [http://localhost:8080/](http://localhost:8080/)

### Что уже сделано
Данная реализация использует минимальный набор функций для тех, кому сложно начать делать этот проект с нуля.

Реализован лифт со следующими функциями:

* Лифт может перевозить только одного пассажира
* Лифт может быть вызван на этаж
* В лифт можно войти, что позволяет после этого осуществить поездку на этаж
* Если пассажир долго бездействует в лифте, то он будет исключен из кабины, чтобы не занимать его
* Есть возможность получения состояния самого лифта, а также иметь информацию о текущих доступных операциях с ним
* Лифт может сломаться и быть починеным
* Лифт предоставляет использующему его пользователю список доступных операций в каждый момент времени (доступность кнопок визуального интерфейса меняется в зависимости от ситуации)

Возможности визуального интерфейса (реализация на Vaadin 24):
* Авторизация отсутствует, но при первом обращении пользователь создается автоматически
* Пользователь может выполнять основные операции с лифтом
* Приложение является сетевым, т.е. два разных пользователя способны влиять друг на друга, т.к. только один пользователь может в один момент времени пользоваться лифтом
* Есть возможность просмотра текущего состояния лифта
* Есть возможность просмотра находящихся в онлайн пользователей приложения

### Какие ошибки можно исправить самостоятельно

* Найди способ сесть в лифт не на своем этаже и исправь эту проблему

### Какие идеи можно реализовать самостоятельно

* Открытие/закрытие дверей лифта, как отдельное состояние
* Лифт может брать более одного человека (учитывает число вошедших и суммарный вес)
* Лифт планирует движение с учетом одновременного вызова на несколько разных этажей
* Возврат лифта на первый этаж при длительном простое
* Вызов лифта на первый этаж в момент открытия домофона подъездной двери
* Лифт учитывает сразу несколько нажатых кнопок в кабине
* На этаже может быть две кнопки вверх/вниз (как в бизнес-центрах)
* Реализация логики, связанной с обращением к лифтеру, который может починить лифт
* *Работа двух и более лифтов в одном подъезде в согласованном между собой состоянии
* *Создание ботов, которые сами появляются время от времени и пользуются лифтом

### Какие технологии можно попробовать добавить самостоятельно

 * Spring security. Добавьте авторизацию для всех пользователей
 * Spring jdbc/data/jpa. Храните пользователей любым интересным для вас способом. Сохраняйте информацию о поездках пассажиров на лифте
 * Rest/Soap/RxJava/Netty Вынесите внутреннюю логику движения лифта в отдельный микросервис и общайтесь с ним через сервисные вызовы
 * JAAS/OAuth2/Keycloak. Авторизацию и доступность пользователей можно также сделать в виде отдельных сервисов
 * Spring cache abstraction. Кэшируйте пользователей
 * H2db. Создавайте собственные unit тесты, которые поднимают H2DB и выполняют проверку корректности работы DAO
 * Apache kafka. Публикуйте события лифта в кафку и создайте собственного потребителя этих данных, чтобы аккумулировать статистику работы лифта
 * Rabbit MQ. Реализуйте очередь событий о поломках лифтов, которые обрабатываются дежурной лифтовой службой
 * Spring Boot Actuator. Прикрутите статистику использования лифта к TSDB и визуализируйте это в Grafana
 * Docker/Podman. Упакуйте приложение и БД в отдельный контейнер/разные контейнеры
 * Spring cloud. Пусть несколько лифтов в виде отдельных приложений объединяются в один подъезд и обрабатывают потребности в перевозке людей. Стартуйте несколько копий отдельных лифтов и имитируйте их отказы, чтобы обеспечить отказоустойчивость вашего кластера
 * Frontend. Вынесите всю визуалку в отдельное приложение. Можете сделать визуалку в виде десктопного или мобильного приложения
