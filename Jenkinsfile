pipeline {
    agent any
    environment {
        HELM_VERSION = "v3.14.4"
        HELM_HOME = "${WORKSPACE}/bin"
        PATH = "${WORKSPACE}/bin:${env.PATH}"
    }

    parameters {
        string(name: 'HELM_CHART_VERSION', defaultValue: '0.1.0', description: 'Helm chart')
    }

    stages {
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
        stage('List files') {
            steps {
                sh 'ls -la $WORKSPACE'
            }
        }
        stage('Install helm chart') {
            steps {
                sh '''
                    helm upgrade --install devops-chart $WORKSPACE/deploy/manifests/devops-chart --version ${params.HELM_CHART_VERSION} -f $WORKSPACE/deploy/manifests/devops-chart/values.yaml
                '''
            }
        }
    }
}
