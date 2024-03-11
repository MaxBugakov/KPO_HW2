// Контроллер меню.
class MenuController {
    companion object {

        // Добавление нового блюда в меню.
        fun add() {
            var flag = false
            var name = ""
            var quantity = 0
            var quantityDimension = ""
            var cost = 0
            var cookingTime = 0
            // Проверка введённых данных на корректность.
            do {
                try {
                    println("Введите название нового блюда")
                    name = readLine()!!
                    println("Введите кол-во нового блюда")
                    quantity = readLine()!!.toInt()
                    println("Введите размерность кол-ва нового блюда")
                    quantityDimension = readLine()!!
                    println("Введите стоимость нового блюда")
                    cost = readLine()!!.toInt()
                    println("Введите время приготовления нового блюда в минутах")
                    cookingTime = readLine()!!.toInt()
                    if (quantity >= 0 && cost>=0 && cookingTime >=0 && name.isNotBlank() && quantityDimension.isNotBlank()) {
                        if (DataBase.menu.find(name) == null) {
                            flag = true
                        }
                        else {
                            println("--------------------------------------")
                            println("Блюдо с таким названием уже существует")
                            println("--------------------------------------")
                        }
                    }
                    else
                        throw Exception()
                }
                catch (e: Exception) {
                    println("-----------------------------")
                    println("Введено некорректное значение")
                    println("-----------------------------")
                }
            } while (!flag)
            // Добавлению блюда.
            val newDish = Dish(name, quantity, quantityDimension, cost, cookingTime)
            DataBase.menu.add(newDish)
            println("Новое блюдо успешно добавлено")
        }

        // Изменение информации о выбранном блюде.
        fun changeDishInfo(selectedDishIndex: Int, selectedDishFieldIndex: Int) {
            // Поля с типом INT.
            if (selectedDishFieldIndex == 2 || selectedDishFieldIndex == 4 || selectedDishFieldIndex == 5) {
                var flag = false
                var value = 0
                // Проверка введённых данных на корректность.
                do {
                    println("Введите новое значения для данного поля")
                    try {
                        value = readLine()!!.trim().toInt()
                        if (value >= 0)
                            flag = true
                        else
                            throw Exception()
                    } catch (e: Exception) {
                        println("-----------------------------------")
                        println("Введённое вами значение некорректно")
                        println("-----------------------------------")
                    }
                } while (!flag)
                // Изменение данных.
                if (selectedDishFieldIndex==2) {
                    DataBase.menu.dishes[selectedDishIndex].quantity = value
                }
                if (selectedDishFieldIndex==4) {
                    DataBase.menu.dishes[selectedDishIndex].cost = value
                }
                if (selectedDishFieldIndex==5) {
                    DataBase.menu.dishes[selectedDishIndex].cookingTime = value
                }
            }
            // Поля с типом STRING.
            else {
                var flag = false
                var value = ""
                // Проверка введённых данных на корректность.
                do {
                    println("Введите новое значения для данного поля")
                    try {
                        value = readLine()!!
                        if (value.trim().isNotBlank())
                            flag = true
                        else
                            throw Exception()
                    } catch (e: Exception) {
                        println("-----------------------------------")
                        println("Введённое вами значение некорректно")
                        println("-----------------------------------")
                    }
                } while (!flag)
                // Изменение данных.
                if (selectedDishFieldIndex==1) {
                    DataBase.menu.dishes[selectedDishIndex].name = value
                }
                if (selectedDishFieldIndex==3) {
                    DataBase.menu.dishes[selectedDishIndex].quantityDimension = value
                }
            }
            // Обновление меню.
            DataBase.menu.update()
            println("Значение поля успешно инзменено")
        }

        // Удаление выбранного блюда из меню.
        fun delete(selectedDishIndex: Int) {
            DataBase.menu.delete(DataBase.menu.dishes[selectedDishIndex])
            println("Блюдо успешно удалено")
        }

    }

}