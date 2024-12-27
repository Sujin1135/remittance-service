plugins {
    alias(libs.plugins.flyway)
}

dependencies {
    implementation(rootProject.libs.bundles.db.libs)
    implementation(rootProject.libs.bundles.flyway)

    runtimeOnly(rootProject.libs.mysql.connector.j)

    testImplementation(rootProject.libs.bundles.test.db)
}
