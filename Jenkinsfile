pipeline {
    agent any
    environment {
        HELM_VERSION = "v3.14.4"
        HELM_HOME = "${WORKSPACE}/bin"
        PATH = "${WORKSPACE}/bin:${env.PATH}"
    }
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
                mkdir -p $HELM_HOME
                curl -fsSL https://get.helm.sh/helm-${HELM_VERSION}-linux-amd64.tar.gz -o helm.tar.gz
                tar -zxvf helm.tar.gz
                mv linux-amd64/helm $HELM_HOME/helm
                chmod +x $HELM_HOME/helm
                helm version
                '''
            }
        }
        stage('Install helm chart') {
            steps {
                sh '''
                    helm upgrade --install devops-chart ./deploy/manifests/devops-chart --version 0.1.0 -f values.yaml
                '''
            }
        }
    }
}
