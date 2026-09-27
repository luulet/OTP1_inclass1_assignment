pipeline {
agent any
stages {
stage('Checkout') {
steps {
git 'https://github.com/luulet/OTP1_inclass1_assignment'
}
}
stage('Build') {
steps {
bat 'mvn clean install' // sh for linux and ios
}
}
stage('Test') {
steps {
bat 'mvn test'
}
}
stage('Code Coverage') {
steps {
bat 'mvn jacoco:report'
}
}
stage('Publish Test Results') {
steps {
junit '**/target/surefire-reports/*.xml'
}
}
stage('Publish Coverage Report') {
steps {
jacoco()
}
}
stage('Docker Build') {
steps {
bat 'docker build -t sampowes/temperature-converter:latest .'
}
}
stage('Docker Run Verify') {
steps {
bat 'docker run --rm sampowes/temperature-converter:latest'
}
}
stage('Docker Push') {
steps {
withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
bat 'docker push sampowes/temperature-converter:latest'
}
}
}
}
}