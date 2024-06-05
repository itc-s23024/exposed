package jp.ac.it_college.stds23024.exposed.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:mariadb://127.0.0.1:3306/example",
        driver = "org.mariadb.jdbc.Driver",
        user = "root",
        password = "maria"
    )
    transaction {
        //実行される SQL をログに出力する設定
        addLogger(StdOutSqlLogger)

        println("----5.4.3----")
        val user = UserTable.selectAll().where {
            UserTable.id eq 101    //UserTable.id.eq(101)
        }.single().let(::User)
        println(user)

        println("----5.4.4----")
        val  userList = UserTable.selectAll().where {
            UserTable.name eq "jiro"
        }.map(::User)
        println(userList)

        println("----5.4.6----")
        val userList2 = UserTable.selectAll().where {
            UserTable.age greaterEq 25
        }.map(::User)
        println(userList2)

        println("----5.4.8---")
        val count = UserTable.selectAll().where {
            UserTable.age greaterEq 25
        }.count()
        println("条件に一致したデータは ${count}行 です。")

/*        println("----5.4.10---")
        val statement = UserTable.insert {
            it[id] = 104
            it[name] = "Sirou"
            it[age] = 18
            it[profile] = "Hello"
        }
        println("${statement.insertedCount}行のレコードを挿入しました") */

 /*       println("----5.4.12----")
        val newUser = listOf(
            User(105, "Goro", 15, "Hello"),
            User(106, "Rokuro", 13, "Hello")
        )
        val statement = UserTable.batchInsert(newUser) { (id, name, age, profile) ->
            this[UserTable.id] = id
            this[UserTable.name] = name
            this[UserTable.age] = age
            this[UserTable.profile] = profile
        }
        println("${statement.count()}行のレコードを挿入しました")  */

        println("----5.4.14----")
            val updateCount = UserTable.update ({
                UserTable.id eq 105
            }) {
                it[profile] = "Bye"
            }
        println("${updateCount}行のレコードを挿入しました")

        println("----5.4.20---")
        val deleteCount = UserTable.deleteWhere {
            UserTable.id eq 102
        }
        println("${deleteCount}行のデータを削除しました")
    }

    // INSERT - CREATE
    // SELECT - READ
    // UPDATE - UPDATE
    // DELETE - DELETE
    //          CRUD
}

object UserTable : IntIdTable("user") {
    val name = varchar("name", 16)
    val age = integer("age")
    val profile = varchar("profile", 64)
}

data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val profile: String
) {
    constructor(row: ResultRow) : this(
        id = row[UserTable.id].value,
        name = row[UserTable.name],
        age = row[UserTable.age],
        profile = row[UserTable.profile]
    )
}