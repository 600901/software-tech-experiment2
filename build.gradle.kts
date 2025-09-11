plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "no.xiv"
version = "0.0.1-SNAPSHOT"
description = "rest api with spring boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")

	constraints {
		implementation("org.apache.commons:commons-lang3:3.14.0") {
			because("Older versions are vulnerable (GHSA-j288-q9x7-2f5v)")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
