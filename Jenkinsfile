pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Try to deploy to Kubernetes'
            }
        }

        stage('Test') {
            steps {
                echo 'Another steps'
            }
        }
        stage('Install Helm') {
            steps {
                sh '''
                curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
                '''
            }
        }
        stage('Deploy with Helm') {
            steps {
                sh 'helm version'
                sh 'helm upgrade --install myapp ./chart --namespace default'
            }
        }
    }
}
