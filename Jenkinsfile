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
                sh 'cp -r frontend/generated-client ../'
            }
        }

        stage('Create backend Docker image') {
            steps {
                sh 'mv ../starter-1.0-SNAPSHOT.jar .'
                sh 'docker build -t bonnie-backend:latest .'
            }
        }

        stage('Build frontend') {
            agent {
                docker {
                    image 'node:16.18.1-alpine'
                    args '-v $PWD/frontend:/frontend'
                }
            }
            steps {
                sh '''
                  cd frontend
                  rm -rf node_modules
                  npm install
                  cd ..
                  cp -r ./frontend ../
                  mv ../generated-client ../frontend/'''
            }
        }

        stage('Create frontend Docker image') {
            steps {
                sh '''
                    mv ../frontend .
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
