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

        stage('Debug Jenkins Variables') {
            steps {
                script {
                    echo "JOB_NAME     = ${env.JOB_NAME}"
                    echo "BUILD_NUMBER = ${env.BUILD_NUMBER}"
                    echo "BUILD_URL    = ${env.BUILD_URL}"
                    echo "GIT_BRANCH   = ${env.GIT_BRANCH}"
                    echo "GIT_COMMIT   = ${env.GIT_COMMIT}"
                }
            }
        }
    }

    post {

        success {
            script {

                def branch = (env.GIT_BRANCH ?: "main").replace("origin/", "")
                def buildUrl = "http://host.docker.internal:8086/job/${env.JOB_NAME}/${env.BUILD_NUMBER}/"
                def consoleUrl = "${buildUrl}console"

                sh """
                curl -X POST http://host.docker.internal:8080/api/builds \
                -H "Content-Type: application/json" \
                -d '{
                    "jobName":"${env.JOB_NAME}",
                    "buildNumber":${env.BUILD_NUMBER},
                    "status":"SUCCESS",
                    "duration":0,
                    "branch":"${branch}",
                    "commitId":"${env.GIT_COMMIT ?: ""}",
                    "buildUrl":"${buildUrl}",
                    "consoleUrl":"${consoleUrl}"
                }'
                """
            }

            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true

            echo "Build Successful!"
        }

        failure {
            script {

                def branch = (env.GIT_BRANCH ?: "main").replace("origin/", "")
                def buildUrl = "http://host.docker.internal:8086/job/${env.JOB_NAME}/${env.BUILD_NUMBER}/"
                def consoleUrl = "${buildUrl}console"

                sh """
                curl -X POST http://host.docker.internal:8080/api/builds \
                -H "Content-Type: application/json" \
                -d '{
                    "jobName":"${env.JOB_NAME}",
                    "buildNumber":${env.BUILD_NUMBER},
                    "status":"FAILURE",
                    "duration":0,
                    "branch":"${branch}",
                    "commitId":"${env.GIT_COMMIT ?: ""}",
                    "buildUrl":"${buildUrl}",
                    "consoleUrl":"${consoleUrl}"
                }'
                """
            }

            echo "Build Failed!"
        }

        always {
            echo "Pipeline Finished."
        }
    }
}
