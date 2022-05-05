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

    stage('deploy api') {
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

        stage('deploy ui') {
                steps{
                  sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                    sshPublisherDesc(
                    configName: "fraudProd",
                    verbose: true,
                    transfers: [
                    sshTransfer(
                    execCommand:"pwd && rm -rf fraud && mkdir fraud"
                  )
                 ])
                ])
                    sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                    sshPublisherDesc(
                    configName: "fraudProd",
                    verbose: true,
                    transfers: [
                    sshTransfer(
                    sourceFiles: "build/**/*",

                  )
                 ])
                ])
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                    sshPublisherDesc(
                    configName: "fraudProd",
                    verbose: true,
                    transfers: [
                    sshTransfer(
                    sourceFiles: "deploy/**/*",

                   execCommand: "pwd && cp -R ./build ./deploy/ && rm -rf ./build && cd fraud/deploy && chmod +x index.js && npm install && sudo service fraud-ui restart"
                  )
                 ])
                ])
                }
            }



  }
}