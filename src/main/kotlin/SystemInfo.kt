import kotlinx.serialization.Serializable

// Информация о системе.
@Serializable
class SystemInfo {
    var usersQuantity : Int
    var sum : Int
    constructor(usersQuantity: Int, sum: Int) {
        this.usersQuantity = usersQuantity
        this.sum = sum
    }
}