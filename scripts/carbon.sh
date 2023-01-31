#!/bin/bash

PROCESSOR_NAME="carbon.jar"

function startProcess() {
        echo "Iniciando a execução do Carbon Apontamentos API"
        java -jar $PROCESSOR_NAME &> /dev/null &

}

function stop() {

        PID=`ps -aux | grep -v grep | grep "$PROCESSOR_NAME" | awk '{print $2}'`

        if [ ! -z "$PID" ]
        then
                echo "Parando a execução do Carbon Apontamentos API ...(" $PID ")"
                kill -9 $PID
        else
                echo "O processo Carbon Apontamentos API."
        fi
}

function start() {

        PID=`ps -aux | grep -v grep | grep "$PROCESSOR_NAME" | awk '{print $2}'`

        if [ ! -z "$PID" ]
        then
                echo "Já existe um processo rodando (" $PID  ")"
                exit 1
        else
                startProcess
        fi
}

case $1 in
        start)
                start
                ;;

        stop)
                stop
                ;;
esac
