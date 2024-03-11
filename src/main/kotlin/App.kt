// Приложение.
class App {
    companion object {
        // Запуск.
        fun start() {

            // Загрузка БД.
            DataBase.load()

            do {
                // Вывод меню.
                printStartLogin()

                // Выбор действия.
                val select = Select("Выберите пункт", 0, 3)
                clearConsoleToContinue()

                // Завершение работы.
                if (select == 0) {
                    break
                }

                // Вход как пользователь.
                if (select == 1) {
                    val user = LoginController.LoginUser()
                    if (user != null) {
                        clearConsoleToContinue()
                        do {
                            // Вывод вариантов действий.
                            printUserOptions()

                            // Выбор варианта действия.
                            val selectedOptionNum = Select("Выберите пункт", 0, 7)
                            clearConsoleToContinue()

                            // Выход.
                            if (selectedOptionNum == 0)
                                break

                            // Просмотр меню.
                            if (selectedOptionNum == 1) {
                                // Вывод меню для пользователя.
                                printMenu()
                            }

                            // Просмотр заказа.
                            if (selectedOptionNum == 2) {
                                printOrder(user)
                            }

                            // Добавление блюда в заказ.
                            if (selectedOptionNum == 3) {
                                // Проверка на готовность заказа.
                                if (user.order.status() == "Готов") {
                                    println("Вы не можете добавлять блюда в заказ, так как он готов. Вам следует оплатить его")
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }
                                // Вывод меню.
                                printMenu()
                                // Проверка на пустое меню.
                                if (DataBase.menu.dishes.size == 0) {
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }
                                // Выбор блюда из меню.
                                val selectedDishIndex = Select(
                                    "Выберите блюдо (пункт), которое вы хотите заказать",
                                    1, DataBase.menu.dishes.size) - 1
                                // Добавление блюда в заказ.
                                OrderController.add(user, selectedDishIndex)
                            }

                            // Удаление блюда из заказа.
                            if (selectedOptionNum == 4) {
                                // Проверка на готовность заказа.
                                if (user.order.status() == "Готов") {
                                    println("Вы не можете удалять блюда из заказа, так как он готов")
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }

                                // Вывод заказа.
                                printOrder(user)
                                // Проверка на пустоту заказа.
                                val activeDishesList = user.order.activeDishes()
                                if (activeDishesList.size == 0) {
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }

                                // Выбор блюда для удаления.
                                val selectedDishIndex = Select(
                                    "Выберите блюдо (пункт), которое вы хотите удалить из заказа",
                                    1, activeDishesList.size) - 1
                                // Удаление блюда.
                                OrderController.delete(user, selectedDishIndex)
                            }

                            // Просмотр статуса заказа.
                            if (selectedOptionNum == 5) {
                                val status = OrderController.status(user)
                                println("Статус заказа: $status")
                            }

                            // Отмена заказа.
                            if (selectedOptionNum == 6) {
                                OrderController.cancel(user)
                            }

                            // Оплата заказа.
                            if (selectedOptionNum == 7) {
                                printOrder(user)
                                OrderController.pay(user)
                            }

                            clearConsole("Введите что угодно, чтобы вернуться на главную")

                        } while (true)
                    } else {
                        clearConsole("Введите что угодно, чтобы вернуться назад")
                        continue
                    }

                }

                // Регистрация пользователя.
                if (select == 2) {
                    if (LoginController.RigisterUser()) {
                        clearConsole("Введите что угодно, чтобы войти")
                        continue
                    } else {
                        clearConsole("Введите что угодно, чтобы вернуться назад")
                        continue
                    }
                }

                // Вход как админ.
                if (select == 3) {
                    if (LoginController.LoginAdmin()) {
                        clearConsoleToContinue()
                        do {
                            // Вывод вариантов действий.
                            printAdminOptions()
                            // Выбор варианта действия.
                            val selectedOptionNum = Select("Выберите, что вы хотите сделать", 0, 5)
                            clearConsoleToContinue()

                            // Выход.
                            if (selectedOptionNum == 0) {
                                break
                            }

                            // Показ меню.
                            if (selectedOptionNum == 1) {
                                // Вывод меню для админа.
                                printMenu()
                            }

                            // Добавление нового блюда.
                            if (selectedOptionNum == 2) {
                                // Вывод меню для админа.
                                printMenu()
                                // Добавление блюда в меню.
                                MenuController.add()
                            }

                            // Изменение информации о блюде.
                            if (selectedOptionNum == 3) {
                                // Вывод меню для админа.
                                printMenu()

                                // Проверка на пустое меню.
                                if (DataBase.menu.dishes.size == 0) {
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }

                                // Выбор блюда из меню, информацию о котором нужно изменить.
                                val selectedDishIndex = Select(
                                    "Выберите блюдо (пункт), данные о котором вы хотите изменить",
                                    1, DataBase.menu.dishes.size
                                ) - 1
                                clearConsoleToContinue()
                                // Вывод полей выбранного блюда.
                                printDishFields(selectedDishIndex)
                                val selectedDishFieldIndex = Select(
                                    "Выберите поле у блюда(пункт)," +
                                            " которое вы хотите изменить", 1, 5
                                )
                                // Изменение информации о выбранном блюде.
                                MenuController.changeDishInfo(selectedDishIndex, selectedDishFieldIndex)
                            }

                            // Удаление блюда из меню.
                            if (selectedOptionNum == 4) {
                                // Вывод меню для админа.
                                printMenu()

                                // Проверка на пустое меню.
                                if (DataBase.menu.dishes.size == 0) {
                                    clearConsole("Введите что угодно, чтобы вернуться на главную")
                                    continue
                                }

                                // Выбор блюда из меню, которое нужно удалить.
                                val selectedDishIndex = Select(
                                    "Выберите блюдо (пункт), которое вы хотите удалить",
                                    1, DataBase.menu.dishes.size
                                ) - 1
                                MenuController.delete(selectedDishIndex)
                            }

                            // Показ информации о системе.
                            if (selectedOptionNum == 5) {
                                printSystemInfo()
                            }

                            clearConsole("Введите что угодно, чтобы вернуться на главную")

                        } while (true)
                    } else {
                        clearConsole("Введите что угодно, чтобы вернуться назад")
                        continue
                    }
                }
            } while (true)
        }

        // Вывод логининга.
        private fun printStartLogin() {
            println("Что вы хотите сделать?")
            println("1. Войти")
            println("2. Зарегестрироваться")
            println("3. Войти как админ")
            println("0. Завершить работу")
        }

        // Вывод вариантов действий для пользователя.
        private fun printUserOptions() {
            println("Что вы хотите сделать?")
            println("1. Посмотреть меню")
            println("2. Посмотреть заказ")
            println("3. Добавить блюдо в заказ")
            println("4. Удалить блюдо из заказа")
            println("5. Посмотреть статус заказа")
            println("6. Отменить заказ")
            println("7. Оплатить заказ")
            println("0. Выйти")
        }

        // Вывод меню для пользователей.
        private fun printMenu() {
            if (DataBase.menu.dishes.size == 0) {
                println("--------------------")
                println("Меню пока что пустое")
                println("--------------------")
                return
            }
            println("Меню")
            for (i in 0..DataBase.menu.dishes.size - 1) {
                print("${i + 1}. ${DataBase.menu.dishes[i]}")
                println()
            }
        }

        // Вывод заказа пользователя.
        private fun printOrder(user: User) {
            val activeDishesList = user.order.activeDishes()
            if (activeDishesList.size == 0) {
                println("-----------------------")
                println("Ваш заказ пока что пуст")
                println("-----------------------")
                return
            }
            println("Ваш заказ")
            for (i in 0..activeDishesList.size - 1) {
                println("${i+1}. ${activeDishesList[i]}")
            }
        }

        // Вывод вариантов действий для админа.
        private fun printAdminOptions() {
            println("Что вы хотите сделать?")
            println("1. Посмотреть меню")
            println("2. Добавить новое блюдо")
            println("3. Изменить информацию о блюде")
            println("4. Удалить блюдо")
            println("5. Посмотреть информацию о системе")
            println("0. Выйти")
        }

        // Изменение информации о блюде.
        private fun printDishFields(selectedDishIndex: Int) {
            val dish = DataBase.menu.dishes[selectedDishIndex]
            println("Информация о блюде")
            println("1. Название: ${dish.name}")
            println("2. Количество: ${dish.quantity}")
            println("3. Размерность количества: ${dish.quantityDimension}")
            println("4. Стоимость: ${dish.cost}")
            println("5. Время приготовления в минутах: ${dish.cookingTime}")
        }

        // Вывод системной информации.
        private fun printSystemInfo() {
            println("Информация о системе")
            println("Количество пользователей в системе: ${DataBase.systemInfoDB.systemInfo.usersQuantity}")
            println("Сумма выручки: ${DataBase.systemInfoDB.systemInfo.sum}")
        }

        // Выбор пункта.
        private fun Select(text: String, leftLimit: Int, rightLimit: Int): Int {
            var flag = false
            var value = 0
            do {
                println(text)
                try {
                    value = readLine()!!.trim().toInt()
                    if (value >= leftLimit && value <= rightLimit)
                        flag = true
                    else
                        throw Exception()
                } catch (e: Exception) {
                    println("---------------------------")
                    println("Такого пункта не существует")
                    println("---------------------------")
                }
            } while (!flag)
            return value
        }

        // Очистка консоли для продолжения.
        fun clearConsoleToContinue() {
            repeat(50) { println() }
        }

        // Очистка консоли.
        fun clearConsole(text: String) {
            println("----------------")
            println(text)
            readLine()
            repeat(50) { println() }
        }
    }
}