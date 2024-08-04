#!/usr/bin/env groovy
properties([
    buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '30'))
])

node {

  def port = 3020  // default port
  def profiles = "qa,no-liquibase" // default port

  try {
    stage('checkout') {
      checkout scm
    }

    stage('check java') {
      sh "java -version"
    }

    stage('clean') {
      sh "chmod +x mvnw"
      sh "./mvnw -ntp clean -P-webapp"
    }
    stage('nohttp') {
      sh "./mvnw -ntp checkstyle:check"
    }

    // stage('backend tests') {
    //     try {
    //         sh "./mvnw -ntp verify -P-webapp"
    //     } catch(err) {
    //         throw err
    //     } finally {
    //         junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
    //     }
    // }

    stage('Packaging') {
      sh "./mvnw -ntp verify -P-webapp -Pqa -DskipTests"
      archiveArtifacts artifacts: '**/target/SmartEoffice-1.0.0.jar', fingerprint: true
    }

    stage('Approve Deployment') {
      timeout(time: 60, unit: "MINUTES") {
        input message: 'Do you want to approve the deployment?', ok: 'Yes'
      }
    }

    stage('Service Port For GIT Branch') {
            // Set the port based on the branch name content
            if (env.BRANCH_NAME.contains('release')) {
                port = 3020
                profiles="qa,no-liquibase";
            } else if (env.BRANCH_NAME.contains('tag')) {
                port = 9010
                profiles="preprod,no-liquibase"
            }
            echo "Determined port for this run: ${port}"
    }

    stage('Stop Service') {
      script {
        def PORT_NUMBER = "${port}"
        sh """
        # Find the process ID using the given port number
        PID=\$(sudo lsof -t -i:"$PORT_NUMBER" || true)

        if [ -z "\$PID" ]; then
           echo "No process found running on port $PORT_NUMBER"
					 exit 0
        else
            echo "Killing process with PID \$PID running on port $PORT_NUMBER"
            sudo kill -9 \$PID
        fi
        """
      }
    }

    stage("Start Service") {
      echo "Initiating deployment"
    sh """
        sudo tmux new-session -d -s SmartEoffice-${port} "java -jar ./target/SmartEoffice-1.0.0.jar --server.port=${port} --spring.profiles.active=${profiles} > /home/logs/SmartEoffice-${port}.log 2>&1"
    """
    }

    stage ('Check Service Running '){
			script {
                sleep(time: 30, unit: 'SECONDS')
                    def PORT_NUMBER = "${port}"  // Adjust this to your desired port number
                    sh """
                        # Find the process ID using the given port number
                        PID=\$(sudo lsof -t -i:"$PORT_NUMBER" || true)

                        if [ -z "\$PID" ]; then
                            echo "No process found running on port $PORT_NUMBER"
                            exit 1
                        else
                            echo "Process with PID \$PID running on port $PORT_NUMBER"
                        fi
                    """
                }
	}

  } catch (Exception e) {
    currentBuild.result = 'FAILURE'
    throw e
  } finally {
    // This block allows for post-build actions, similar to the post {} block in a declarative pipeline.
    // For example, to always send notifications regardless of build result:
    // notifyBuild(currentBuild.result)
  }
}