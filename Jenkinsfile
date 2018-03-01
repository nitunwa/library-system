pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat(script: 'mvn clean install', returnStatus: true, returnStdout: true)
      }
    }
  }
}