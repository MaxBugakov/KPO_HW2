import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

// СУБД для админов.
class AdminDB {
    private val path = "AdminDB.json"
    var admins = mutableListOf<Admin>()

    // Добавление пользователя.
    fun add(newAdmin: Admin) {
        admins.add(newAdmin)
        update()
    }

    // Удаление пользователя.
    fun delete(admin: Admin) {
        admins.remove(admin)
        update()
    }

    // Поиск пользователя.
    fun find(name: String) : Admin? {
        val admin = admins.find { it.name == name }
        return admin
    }

    // Загрузка БД.
    fun load() {
        try {
            val jsonString = File(path).readText()
            val adminsList: List<Admin> = Json.decodeFromString(jsonString)
            admins = adminsList.toMutableList()
        }
        catch (e: Exception) {
            // Первая инициализация БД.
            // Создание дефолтного админа.
            val admin = Admin("admin", "admin")
            admin.hashPassword()
            admins = mutableListOf<Admin>()
            admins.add(admin)
        }
    }

    // Обновление БД.
    fun update() {
        val jsonString = Json { prettyPrint = true }.encodeToString(admins)
        File(path).writeText(jsonString)
    }

}