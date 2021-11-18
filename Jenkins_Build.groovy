//Declarative pipeline
pipeline{
    agent any
    stages{
        stage("Cloning code") {
            steps {
               println "Git clone URL"
               sh "ls -l"
            }
        }
        stage("Build Code") {
            steps {
               println "mvn clean package"
               sh "mvn clean package"
               sh "ls -l /target/"
            }
        }
        stage("Uploading build files to S3 bucket") {
            steps {
                println "upload files to S3"
            }
        }
    }
}