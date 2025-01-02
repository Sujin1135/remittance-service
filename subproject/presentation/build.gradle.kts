dependencies {
    implementation(project(":subproject:domain"))
    implementation(project(":subproject:application"))

    implementation(rootProject.libs.spring.validation)
    implementation(rootProject.libs.swagger)
}
