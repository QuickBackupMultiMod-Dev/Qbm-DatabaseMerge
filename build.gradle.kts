plugins {
    kotlin("jvm") version "2.0.21" // 确保使用正确的Kotlin版本
    id("org.graalvm.buildtools.native") version "0.10.6"
    application
}

group = "io.github.skydynamic"
version = "1.0"

application {
    mainClass.set("io.github.skydynamic.databaseMerge.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.morphia.morphia:morphia-core:2.3.9")
    implementation("de.bwaldvogel:mongo-java-server:1.45.0")
    implementation("de.bwaldvogel:mongo-java-server-core:1.45.0")
    implementation("de.bwaldvogel:mongo-java-server-h2-backend:1.45.0")
    implementation("org.mongodb:mongodb-driver-sync:4.11.1")
    implementation("org.mongodb:mongodb-driver-core:4.11.1")
    implementation("org.mongodb:bson:4.11.1")
    implementation("com.h2database:h2:2.2.224")

    implementation("org.jetbrains.exposed:exposed-core:0.57.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.57.0")
    implementation("com.h2database:h2:2.2.224")

    implementation("com.github.ajalt.clikt:clikt:5.0.3")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("org.slf4j:slf4j-log4j12:2.0.7")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

graalvmNative {
    binaries {
        named("main") {
            mainClass.set("io.github.skydynamic.databaseMerge.MainKt")
        }
    }

    binaries.all {
        buildArgs.addAll(listOf(
            "-H:CrossCompileEnabled=true",
            "-H:CrossCompileTargetPlatform=linux-amd64"
        ))
    }
}
