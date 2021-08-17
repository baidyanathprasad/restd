pipeline {
    agent any
    // Test comment
    stages {
        stage('Build') {
            steps {
                echo "Building.."
                sh "./gradlew clean && ./gradlew assemble"
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                sh "./gradlew test"
            }
        }
        stage('Lint Check') {
            steps {
                script {
                    url = "http://localhost:8000/swagger-sample.json"
                    echo "Lint Checking.."
                    sh "java -jar build/libs/restd-1.0-SNAPSHOT-all.jar $url"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying...."
            }
        }
    }
}
