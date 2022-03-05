plugins {
    java
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

group = "tech.zone84.examples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("io.projectreactor:reactor-bom:2020.0.16")
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}


dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("io.projectreactor:reactor-core")
    implementation("org.slf4j:slf4j-api:1.7.35")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.10")
}
