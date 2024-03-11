import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

// СУБД для системой информации.
class SystemInfoDB {
    private val path = "SystemInfo.json"
    var systemInfo = SystemInfo(0, 0)

    // Добавление суммы к выручке.
    fun addSum(newSum: Int) {
        systemInfo.sum += newSum
        update()
    }

    // Добавление пользователя.
    fun addUser() {
        systemInfo.usersQuantity += 1
        update()
    }

    // Загрузка БД.
    fun load() {
        try {
            val jsonString = File(path).readText()
            val systemInfoList: SystemInfo = Json.decodeFromString(jsonString)
            systemInfo = systemInfoList
        }
        catch (e: Exception) {
            systemInfo = SystemInfo(0, 0)
        }
    }

    // Обновление БД.
    fun update() {
        val jsonString = Json { prettyPrint = true }.encodeToString(systemInfo)
        File(path).writeText(jsonString)
    }
}