pipeline {
    agent any
    stages {
        stage('Build backend') {
            agent {
                docker {
                    image 'maven:3.8.6-openjdk-18'
                    args '-v /root/.m2:/root/.m2 -v "$(pwd)":/tmp'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package install'
                sh 'mvn -f starter/pom.xml package spring-boot:repackage'
                sh 'ls -la starter/target'
                sh 'cp starter/target/./starter/target/starter-1.0-SNAPSHOT.jar /tmp'
            }
        }
        stage('Create backend Docker image') {
            steps {
                sh 'ls -la'
                sh 'ls -la starter/'
                sh 'ls -la starter/target'
                sh 'docker build -t bonnie-backend:latest .'
            }
        }
        stage('Create frontend Docker image') {
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
