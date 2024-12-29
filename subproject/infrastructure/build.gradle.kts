plugins {
    alias(libs.plugins.flyway)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.flyway.mysql)
    }
}

dependencies {
    implementation(project(":subproject:domain"))
    implementation(rootProject.libs.bundles.db.libs)
    implementation(rootProject.libs.bundles.flyway)

    runtimeOnly(rootProject.libs.mysql.connector.j)

    testImplementation(rootProject.libs.bundles.test.db)
}

val dbHost: String = System.getenv("DB_HOST") ?: "127.0.0.1"
val dbPort: String = System.getenv("DB_PORT") ?: "3306"
val dbSchema: String = System.getenv("DB_SCHEMA") ?: "remittance"
val dbUser: String = System.getenv("DB_USER") ?: "root"
val dbPassword: String = System.getenv("DB_PASSWORD") ?: "password123!"

flyway {
    driver = "com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://$dbHost:$dbPort/$dbSchema"
    baselineVersion = "1"
    baselineDescription = "true"
    user = dbUser
    password = dbPassword
    connectRetries = 60
    locations =
        listOf("filesystem:src/main/resources/db/migration/")
            .toTypedArray()
    cleanDisabled = false
}
