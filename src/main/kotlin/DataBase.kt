// База данных.
class DataBase {
    companion object {
        val userDB = UserDB()
        val adminDB = AdminDB()
        val menu = Menu()
        val systemInfoDB = SystemInfoDB()

        // Загрузка БД.
        fun load() {
            userDB.load()
            adminDB.load()
            menu.load()
            systemInfoDB.load()
        }
    }
}