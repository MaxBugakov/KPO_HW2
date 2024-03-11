import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

// СУБД для пользователей.
class UserDB {
    private val path = "UserDB.json"
    var users = mutableListOf<User>()

    // Добавление пользователя.
    fun add(newUser: User) {
        users.add(newUser)
        DataBase.systemInfoDB.addUser()
        update()
    }

    // Удаление пользователя.
    fun delete(user: User) {
        users.remove(user)
        update()
    }

    // Поиск пользователя.
    fun find(name: String) : User? {
        val user = users.find { it.name == name }
        return user
    }

    // Загрузка БД.
    fun load() {
        try {
            val jsonString = File(path).readText()
            val usersList: List<User> = Json.decodeFromString(jsonString)
            users = usersList.toMutableList()
        }
        catch (e: Exception) {
            users = mutableListOf<User>()
        }
    }

    // Обновление БД.
    fun update() {
        val jsonString = Json { prettyPrint = true }.encodeToString(users)
        File(path).writeText(jsonString)
    }

}