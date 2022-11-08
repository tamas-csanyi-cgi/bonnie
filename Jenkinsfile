pipeline {
    agent any
    stages {
        stage('Build backend') {
            agent {
                docker {
                    image 'maven:3.8.6-openjdk-18'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package install'
                sh 'mvn -f starter/pom.xml package spring-boot:repackage'
                sh 'cp starter/target/starter-1.0-SNAPSHOT.jar ../'
                sh 'cp frontend/generated-client ../'
            }
        }

        stage('Create backend Docker image') {
            steps {
                sh 'mv ../starter-1.0-SNAPSHOT.jar .'
                sh 'docker build -t bonnie-backend:latest .'
                sh 'rm starter-1.0-SNAPSHOT.jar'
            }
        }

        stage('Create frontend Docker image') {
            steps {
                sh '''cd frontend
                    mv ../generated-client .
                    docker build -f Dockerfile-frontend -t bonnie-ui:latest .'''
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
