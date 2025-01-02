dependencies {
    implementation(project(":subproject:domain"))

    testImplementation(project(":subproject:infrastructure"))

    testFixturesImplementation(project(":subproject:domain"))

    testImplementation(rootProject.libs.bundles.test.db)
    testImplementation(rootProject.libs.bundles.flyway)

    testFixturesImplementation(rootProject.libs.fixture.monkey)
}
