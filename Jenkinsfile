pipeline {
    agent any

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Cloning source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling application...'
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application...'
                sh './mvnw clean package'
            }
        }
    }

    post {

        always {
            echo 'Pipeline execution completed.'
        }

        success {
            echo 'Build Successful!'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }

        failure {
            echo 'Build Failed!'
        }
    }
}
