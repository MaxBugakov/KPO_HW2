// Контроллер заказов.
class OrderController {
    companion object {

        // Добавление нового блюда в меню.
        fun add(user: User, selectedDishIndex: Int) {
            user.order.addDish(DataBase.menu.dishes[selectedDishIndex])
        }

        fun delete(user: User, selectedDishIndex: Int) {
            if (user.order.status() == "Готов")
            {
                println("Пока вы выбирали блюдо для удаления, ваш заказ был приготовлен, поэтому вы не можете удалить блюдо")
                return
            }
            var counter = -1
            for (i in 0..user.order.dishes.size - 1)
            {
                if (user.order.dishesActive[i]) {
                    counter += 1
                    if (counter == selectedDishIndex) {
                        user.order.removeDish(i)
                        println("Блюдо удалено из заказа")
                        return
                    }
                }
            }
        }

        // Статус заказа.
        fun status(user: User) : String {
            val status = user.order.status()
            return status
        }

        // Отмена заказа.
        fun cancel(user: User) {
            if (user.order.status() == "Заказ не сделан")
            {
                println("Вы ещё не сделали заказ")
                return
            }
            if (user.order.status() == "Готов")
            {
                println("Ваш заказ уже готов, вы не можете его отменить")
                return
            }

            var flag = false
            var value = ""
            // Проверка введённых данных на корректность.
            do {
                println("Вы точно хотите отменить заказ? Введите (Y/N)")
                try {
                    value = readLine()!!.trim()
                    if (value == "Y" || value == "N")
                        flag = true
                    else
                        throw Exception()
                } catch (e: Exception) {
                    println("-----------------------------------")
                    println("Введённое вами значение некорректно")
                    println("-----------------------------------")
                }
            } while (!flag)
            if (value == "Y") {
                if (user.order.status() == "Готов")
                {
                    println("Пока вы хотели отменить заказ, ваш заказ был приготовлен, поэтому вы не можете его отменить")
                    return
                }
                user.order.cancelCooking()
                println("Ваш заказ отменён")
            }
        }

        // Оплата заказа.
        fun pay(user: User) {
            if (user.order.status() == "Заказ не сделан")
            {
                println("Вы ещё не сделали заказ")
                return
            }
            if (user.order.status() == "Готовится")
            {
                println("Ваш заказ ещё не готов")
                return
            }

            println("Сумма заказа: ${user.order.sum()}")

            println("Введите что угодно, чтобы оплатить заказ")
            readLine()

            user.order.pay()
            println("Ваш заказ оплачен")
        }
    }
}