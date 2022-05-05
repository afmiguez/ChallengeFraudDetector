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

    stage('deploy ui') {
        steps{
          sshPublisher(
            continueOnError: false, failOnError: true,
            publishers: [
                sshPublisherDesc(
                    configName: "fraudProd",
                    verbose: true,
                    transfers: [
                        sshTransfer(execCommand:" rm -rf fraud && mkdir fraud"),
                        sshTransfer(sourceFiles: "client/deploy/**/*",),
                        sshTransfer(
                            remoteDirectory:"deploy",
                            sourceFiles: "client/build/**/*",
                        ),
                    ]
                ),
                sshPublisherDesc(
                    configName: "fraudProd",
                    verbose: true,
                    transfers: [
                        sshTransfer(execCommand: "cd /root/fraud/client/deploy && chmod +x index.js && npm install && sudo service fraud-ui restart")
                    ]
                )
        ]
        )
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

  }
}