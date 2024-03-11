import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

// Меню (СУБД для блюд).
class Menu {
    private val path = "Menu.json"
    var dishes = mutableListOf<Dish>()

    // Добавление блюда.
    fun add(newDish: Dish) {
        dishes.add(newDish)
        update()
    }

    // Удаление блюда.
    fun delete(dish: Dish) {
        dishes.remove(dish)
        update()
    }

    // Поиск блюда.
    fun find(name: String) : Dish? {
        val dish = dishes.find { it.name == name }
        return dish
    }

    // Загрузка БД.
    fun load() {
        try {
            val jsonString = File(path).readText()
            val dishesList: List<Dish> = Json.decodeFromString(jsonString)
            dishes = dishesList.toMutableList()
        }
        catch (e: Exception) {
            // Первая инициализация БД.
            dishes = mutableListOf<Dish>()
        }
    }

    // Обновление БД.
    fun update() {
        val jsonString = Json { prettyPrint = true }.encodeToString(dishes)
        File(path).writeText(jsonString)
    }
}