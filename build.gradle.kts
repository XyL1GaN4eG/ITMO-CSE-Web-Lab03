plugins {
    id("java")
    id("io.freefair.lombok") version "8.10"
    id("war")
}

group = "web"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("javax:javaee-web-api:8.0.1")
    implementation("javax.faces:jsf-api:2.1")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.primefaces:primefaces:13.0.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.projectlombok:lombok:1.18.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.slf4j:slf4j-api:1.7.36")
}



tasks.withType<War> {
    from("src/main/webapp") {
        into("/")
    }
    exclude("META-INF/*.xml")
    exclude("META-INF/services/*")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.create("deploy") {
    dependsOn("war")

    doLast {
        exec {
            workingDir(".")
            commandLine("bash", "deploy.sh")
        }
    }
}