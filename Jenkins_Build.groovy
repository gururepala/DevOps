//Declarative pipeline
pipeline{
    agent any
    environment {
        BRANCH = "${env.BRANCH_NAME}"
    }
    stages{
        stage("Cloning code") {
            steps {
               println "Git clone URL"
               checkout([$class: 'GitSCM', branches: [[name: '*/${BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'jenkins-user-github', url: 'https://github.com/gururepala/boxfuse-sample-java-war-hello.git']]])
               sh "ls -l"
            }
        }
        stage("Build Code") {
            steps {
               println "mvn clean package"
               sh "mvn clean package"
               sh "ls -l target/"
            }
        }
        stage("Uploading build files to S3 bucket") {
            steps {
                println "upload files to S3"
                sh "echo $BUILD_NUMBER"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://mydeployedprojects/${BRANCH}/${BUILD_NUMBER}/"
            }
        }
    }
}