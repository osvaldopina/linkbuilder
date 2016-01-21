#!/usr/bin/env bash


#
#
#  Check, in pom.xml, if current version is
#  a snapshot or not
#
#
isSnapshotVersion() {
  if grep -q "<version>.*-SNAPSHOT</version>" pom.xml
  then
      return 0;
  else
      return 1;
  fi
}

#
#
#  Check, in pom.xml, if current version is
#  a final versio or not
#
#
isFinalVersion() {
  if isSnapshotVersion
  then
    return 1;
  else
    return 0;
  fi
}

#
#
#  Check if travis is trying to build a
#  pull request or not
#
#
isPullRequest() {
  if [ "${TRAVIS_PULL_REQUEST}" == "false" ]
  then
    return 1;
  else
    return 0;
  fi
}

if isPullRequest
then
    if isFinalVersion
    then
        echo "************************************************************"
        echo ""
        echo "Pull Request should never have final versions in pom.xml!!!!"
        echo ""
        echo "************************************************************"
        exit -1
    else
        echo "************************************************************"
        echo ""
        echo "executing tests for pull request ${TRAVIS_PULL_REQUEST}..."
        echo ""
        echo "************************************************************"
        mvn test --settings travis-ci-maven-settings.xml
    fi
elif isSnapshotVersion
then
    echo "*********************"
    echo ""
    echo "deploying snapshot..."
    echo ""
    echo "*********************"
    mvn deploy --settings travis-ci-maven-settings.xml -Dgpg.skip=true
else
    echo "**************************************"
    echo ""
    echo "executing tests for a final version..."
    echo ""
    echo "**************************************"
    mvn test --settings travis-ci-maven-settings.xml
fi