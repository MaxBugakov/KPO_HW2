import kotlinx.serialization.Serializable
import java.security.MessageDigest

// Админ.
@Serializable
class Admin {
    var name: String
    var password: String

    // Конструктор.
    constructor(name: String, password: String) {
        this.name = name
        this.password = password
    }

    // Шифрование пароля админа.
    fun hashPassword() {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        password =  digest.joinToString("") { "%02x".format(it) }
    }

    // Переопределение сравнения.
    override fun equals(other: Any?): Boolean {
        val otherAdmin = other as? Admin
        if (otherAdmin != null) {
            return name == otherAdmin.name
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