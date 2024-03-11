// Контроллер логининга и регистрация.
class LoginController {
    companion object {

        // Вход для пользователей.
        fun LoginUser() : User? {
            println("Введите логин")
            var name = readLine()!!
            println("Введите пароль")
            var password = readLine()!!
            var user = User(name, password)
            user.hashPassword()

            // Поиск пользователя в БД.
            val findedUser = DataBase.userDB.find(name)
            if (findedUser==null) {
                println("-----------------------")
                println("Вы что-то ввели неверно")
                println("-----------------------")
                return null
            }
            else {
                // Проверка на правильность введённого пароля.
                if (findedUser.password == user.password) {
                    println("Вы вошли")
                    return user
                }
                else {
                    println("-----------------------")
                    println("Вы что-то ввели неверно")
                    println("-----------------------")
                    return null
                }
            }

        }

        // Регистрация для пользователей.
        fun RigisterUser() : Boolean {
            println("Придумайте логин")
            var name = readLine()!!
            println("Придумайте пароль")
            var password = readLine()!!

            // Проверка на пустое имя.
            if (name.trim().isBlank()) {
                println("------------------------")
                println("Имя не может быть пустым")
                println("------------------------")
                return false
            }

            // Проверка на пустой пароль.
            if (password.trim().isBlank())
            {
                println("---------------------------")
                println("Пароль не может быть пустым")
                println("---------------------------")
                return false
            }

            // Проверка на существование пользователя с таким же именем.
            if (DataBase.userDB.find(name) != null) {
                println("-------------------------------------------")
                println("Пользователь с таким логином уже существует")
                println("-------------------------------------------")
                return false
            }

            val newUser = User(name, password)
            newUser.hashPassword()
            DataBase.userDB.add(newUser)
            println("Вы зарегестрировались")
            return true
        }

        // Вход для админов.
        fun LoginAdmin() : Boolean {
            println("Введите логин")
            var name = readLine()!!
            println("Введите пароль")
            var password = readLine()!!
            var admin = User(name, password)
            admin.hashPassword()

            // Поиск админа в БД.
            val findedAdmin = DataBase.adminDB.find(name)
            if (findedAdmin==null) {
                println("-----------------------")
                println("Вы что-то ввели неверно")
                println("-----------------------")
                return false
            }
            else {
                // Проверка на правильность введённого пароля.
                if (findedAdmin.password == admin.password) {
                    println("Вы вошли")
                    return true
                }
                else {
                    println("-----------------------")
                    println("Вы что-то ввели неверно")
                    println("-----------------------")
                    return false
                }
            }
        }

    }
}