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
            }
        }

        stage('Create backend Docker image') {
            steps {
                sh 'cp ../starter-1.0-SNAPSHOT.jar .'
                sh 'ls -la'
                sh 'docker build -t bonnie-backend:latest .'
                sh 'rm starter-1.0-SNAPSHOT.jar'
                sh 'rm ../starter-1.0-SNAPSHOT.jar'
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
