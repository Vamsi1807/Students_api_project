pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
    }

    post {

        success {
            script {

                def duration = currentBuild.durationString.replace(' and counting', '')

                sh """
                curl -X POST http://host.docker.internal:8080/api/builds \
                -H "Content-Type: application/json" \
                -d '{
                    "jobName":"${env.JOB_NAME}",
                    "buildNumber":${env.BUILD_NUMBER},
                    "status":"SUCCESS",
                    "duration":0,
                    "branch":"${env.GIT_BRANCH ?: "main"}",
                    "commitId":"${env.GIT_COMMIT ?: ""}",
                    "buildUrl":"${env.BUILD_URL}",
                    "consoleUrl":"${env.BUILD_URL}console"
                }'
                """
            }

            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true

            echo 'Build Successful!'
        }

        failure {

            sh """
            curl -X POST http://host.docker.internal:8080/api/builds \
            -H "Content-Type: application/json" \
            -d '{
                "jobName":"${env.JOB_NAME}",
                "buildNumber":${env.BUILD_NUMBER},
                "status":"FAILURE",
                "duration":0,
                "branch":"${env.GIT_BRANCH ?: "main"}",
                "commitId":"${env.GIT_COMMIT ?: ""}",
                "buildUrl":"${env.BUILD_URL}",
                "consoleUrl":"${env.BUILD_URL}console"
            }'
            """

            echo 'Build Failed!'
        }

        always {
            echo 'Pipeline Finished.'
        }
    }
}
