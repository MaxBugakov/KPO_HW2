import kotlinx.serialization.Serializable

// Блюдо.
@Serializable
class Dish {
    var name: String
    var quantity: Int
    var quantityDimension: String
    var cost: Int
    var cookingTime: Int

    // Конструктор.
    constructor(name: String, quantity: Int, quantityDimension: String, cost: Int, cookingTime: Int) {
        this.name = name
        this.quantity = quantity
        this.quantityDimension = quantityDimension
        this.cost = cost
        this.cookingTime = cookingTime
    }

    // Переопределения строки.
    override fun toString(): String {
        return "$name ..... $quantity $quantityDimension  $cost руб  Время приготовления: $cookingTime мин"
    }

    // Переопределение сравнения.
    override fun equals(other: Any?): Boolean {
        val otherDish = other as? Dish
        if (otherDish != null) {
            return name == otherDish.name
        } else {
            return false
        }
    }

    // Переопределение хэша.
    override fun hashCode(): Int {
        val result = name.hashCode()
        return result
    }
}