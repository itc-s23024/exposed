package jp.ac.it_college.stds23024.exposed.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:mariadb://127.0.0.1:3306/example",
        driver = "org.mariadb.jdbc.Driver",
        user = "root",
        password = "maria"
    )
    transaction {
        addLogger(StdOutSqlLogger)

        println("----5.4.3----")
        val user = UserEntity.findById(101)
        println(user)

        println("----5.4.4----")
        UserEntity.find {
            UserTable.name eq "Jiro"
        }.forEach(::println)

        println("----5.4.6----")
        UserEntity.find {
            UserTable.age greaterEq 25
        }.forEach(::println)

        println("----5.4.8----")
        val count = UserEntity.find {
            UserTable.age greaterEq 25
        }.count()
        println("count: $count")

/*        println("----5.4.10----")
        val newUser = UserEntity.new(104){
            name = "Shiro"
            age = 18
            profile = "Hello"
        }
        println("newUser: $newUser")
*/

 /*       println("----5.4.12----")
        UserEntity.new(105) {
            name = "Goro"
            age = 15
            profile = "Hello"
        }.let(::println)
        UserEntity.new(106) {
            name = "Goro"
            age = 13
            profile = "Hello"
        }
  */

        println("----5.4.14----")
        val goro = UserEntity.findById(105)
        goro?.profile = "Bye"
        println("update User: $goro")
        val goro2 = UserEntity.findByIdAndUpdate(105) {
            it.profile = "Bye-Bye"
        }
        println("goro2: $goro2")

        println("-----5.4.16----")
        UserEntity.find{
            UserTable.age lessEq  15
        }.forEach {
            it.profile = "See you"
        }

        println("----5.4.20----")
        val saburo = UserEntity.findById(103)
        saburo?.delete()
    }
}

object UserTable : IntIdTable("user") {
    val name = varchar("name", 16)
    val age = integer("age")
    val profile = varchar("profile", 64)
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UserTable)

    var name by UserTable.name
    var age by UserTable.age
    var profile by UserTable.profile

    override fun toString() = """
        UserEntity(id=$id, name=$name, age=$age, profile=$profile)
        """.trimIndent()
}