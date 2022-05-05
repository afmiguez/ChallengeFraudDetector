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

    stage('SSH transfer') {
         steps {
          sshPublisher(
           continueOnError: false, failOnError: true,
           publishers: [
            sshPublisherDesc(
             configName: "prod",
             verbose: true,
             transfers: [
              sshTransfer(
               sourceFiles: "**/*.jar",
               execCommand: "mv ./build/libs/*.jar ./fraud-api.jar && rm -rf ./build && chmod +x ./fraud-api.jar && sudo service fraud-api restart"
              )
             ])
           ])
         }
        }


  }
}