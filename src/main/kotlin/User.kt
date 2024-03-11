import kotlinx.serialization.Serializable
import java.security.MessageDigest
import kotlinx.serialization.Transient

// Пользователь.
@Serializable
class User {
    var name: String
    var password: String
    @Transient
    var order: Order = Order()

    // Конструктор.
    constructor(name: String, password: String) {
        this.name = name
        this.password = password
    }

    // Шифрование пароля пользователя.
    fun hashPassword() {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        password =  digest.joinToString("") { "%02x".format(it) }
    }

    // Переопределение сравнения.
    override fun equals(other: Any?): Boolean {
        val otherUser = other as? User
        if (otherUser != null) {
            return name == otherUser.name
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