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
                sh 'cat /usr/share/maven/conf/settings.xml'
                sh 'mvn -B -DskipTests --settings /usr/share/maven/conf/settings.xml -X clean | grep setting'
                sh 'mvn -B -DskipTests --settings /usr/share/maven/conf/settings.xml clean package install'
                sh 'cd starter && mvn --settings /usr/share/maven/conf/settings.xml clean package spring-boot:repackage'
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
