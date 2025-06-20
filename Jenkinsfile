pipeline {
  agent {
    dockerfile true
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  triggers {
    cron('@midnight')
  }

  stages {
    stage('test') {
      steps {
        script {
          maven cmd: 'clean verify'
        }
        recordIssues tools: [java()], qualityGates: [[threshold: 1, type: 'TOTAL']]
        recordIssues tools: [mavenConsole()], qualityGates: [[threshold: 1, type: 'TOTAL']]
        junit 'target/surefire-reports/**/*.xml' 
        archiveArtifacts artifacts: 'target/ivy.jar'
      }
    }
  }
}
