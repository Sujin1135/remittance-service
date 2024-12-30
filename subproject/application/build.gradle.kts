dependencies {
    implementation(project(":subproject:domain"))

    testImplementation(project(":subproject:infrastructure"))

    testImplementation(rootProject.libs.bundles.test.db)
    testImplementation(rootProject.libs.bundles.flyway)
}
