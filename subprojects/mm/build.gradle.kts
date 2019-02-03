plugins{
    id("java-common")
}

dependencies{
    compile(project(":theta-core"))
    compile(project(":theta-xta"))
    compile(project(":theta-common"))
}


tasks.withType<Test> {
    useJUnitPlatform()
}