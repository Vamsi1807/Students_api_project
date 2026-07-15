pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Cloning source code...'
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling application...'
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application...'
                sh './mvnw clean package -DskipTests'
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
        }

        failure {
            echo 'Build Failed!'
        }
    }
}
