pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-18'
            args '-v /tmp/jenkins/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build backend') {
            steps {
                sh 'export'
                sh 'mvn -B -DskipTests -X clean | grep setting'
                sh 'mvn -B -DskipTests clean package install'
                sh 'cd starter && mvn clean package spring-boot:repackage'
                sh 'cd .. && docker build -t bonnie-backend:latest .'
            }
        }
        stage('Build frontend') {
            steps {
                sh 'cd frontend'
                sh 'docker build -t bonnie-ui:latest .'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
