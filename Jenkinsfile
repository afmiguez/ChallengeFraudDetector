pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh './gradlew test'
      }
    }

    stage('build frontend') {
      steps {
        sh './gradlew assembleFrontend'
      }
    }

    stage('build api') {
      steps {
        sh './gradlew clean shadowJar'
      }
    }

  }
}