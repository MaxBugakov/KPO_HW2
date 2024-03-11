import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// Заказ.
class Order {
    var dishes = mutableListOf<Dish>()
    var dishesActive = mutableListOf<Boolean>()
    var cookingJobStatus: Int = 0

    // Добавление блюда в заказ.
    fun addDish(dish: Dish) {
        dishes.add(dish)
        dishesActive.add(true)
        // Проверка на существование заказа.
        if (cookingJobStatus == 0)
            startCooking()
    }

    // Удаление блюда из заказа.
    fun removeDish(index: Int) {
        dishesActive[index] = false
    }

    // Статус заказа.
    fun status() : String {
        if (cookingJobStatus == 0)
            return "Заказ не сделан"
        if (cookingJobStatus == 1)
            return "Готовится"
        if (cookingJobStatus == 2)
            return "Готов"
        return ""
    }

    // Отмена заказа.
    fun cancelCooking() {
        dishes = mutableListOf<Dish>()
        dishesActive = mutableListOf<Boolean>()
        cookingJobStatus = 0
    }

    // Приготовление заказа.
    fun startCooking() {
        // Запуск корутины.
        GlobalScope.launch {
            cookingJobStatus = 1
            var iterator = -1
            do {
                try {
                    iterator += 1
                    for (i in 0..dishes[iterator].cookingTime - 1) {
                        if (dishesActive[iterator]) {
                            delay(1000)
                        }
                        else
                            continue
                    }
                }
                catch (e: Exception) {
                    break
                }
            } while (true)

            if (activeDishes().size == 0)
                cookingJobStatus = 0
            else
                cookingJobStatus = 2
            cancel()
        }

    }

    // Сумма заказа.
    fun sum() : Int {
        var sum = 0
        for (i in 0..dishes.size-1) {
            if (dishesActive[i])
                sum += dishes[i].cost
        }
        return sum
    }

    // Активные блюда в заказе.
    fun activeDishes() : MutableList<Dish> {
        val activeDishes = mutableListOf<Dish>()
        for (i in 0..dishes.size-1) {
            if (dishesActive[i])
                activeDishes.add(dishes[i])
        }
        return activeDishes
    }

    // Оплата заказа.
    fun pay() {
        DataBase.systemInfoDB.addSum(sum())
        cookingJobStatus = 0
        dishes = mutableListOf<Dish>()
        dishesActive = mutableListOf<Boolean>()
    }
}