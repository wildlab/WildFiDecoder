#!/bin/bash

# Function to detect operating system
detect_os() {
    case "$(uname -s)" in
        Darwin)
            echo "Running on macOS"
            OS="macOS"
            ;;
        Linux)
            echo "Running on Linux"
            OS="Linux"
            ;;
        CYGWIN*|MINGW32*|MSYS*|MINGW*)
            echo "Running on Windows"
            OS="Windows"
            ;;
        *)
            echo "Unsupported OS"
            exit 1
            ;;
    esac
}

# Function to run the JAR
run_jar() {
    if [ -z "$JAVA_HOME" ]; then
        JAVA_CMD=$(which java)
    else
        JAVA_CMD="$JAVA_HOME/bin/java"
    fi

    if [ -x "$JAVA_CMD" ]; then
        echo "Using Java at: $JAVA_CMD"
    else
        echo "Java not found! Please install JRE or set JAVA_HOME."
        exit 1
    fi
    
    # Check if the script is executed directly or from PATH and resolve the full path
    if [ -n "$BASH_SOURCE" ]; then
        # Use $BASH_SOURCE to reliably find the path even if the script is sourced
        SCRIPT_PATH=$(readlink -f "$BASH_SOURCE")
    else
        # Fallback to which if $BASH_SOURCE is not available (e.g., older Bash versions)
        SCRIPT_PATH=$(which "$0")
    fi

    # Get the directory where the script resides
    SCRIPT_DIR=$(dirname "$SCRIPT_PATH")
      
    # The JAR file name (replace this with your actual JAR file name)
    JAR_FILE="WildFiDecoderStandalone.jar"
    
    # Execute the JAR
    JAR_FILE_WITH_DIRECTORY="$SCRIPT_DIR/$JAR_FILE"
    
    echo "$JAR_FILE_WITH_DIRECTORY"

    if [ ! -f "$JAR_FILE_WITH_DIRECTORY" ]; then
        echo "JAR file $JAR_FILE_WITH_DIRECTORY not found in the current directory."
        exit 1
    fi
	
	# -Xmx%HeapMemoryMB%m
    "$JAVA_CMD" -Xms2g  -XX:+UseG1GC -XX:ParallelGCThreads=4 -XX:ConcGCThreads=2 -XX:+UseAdaptiveSizePolicy -XX:MaxGCPauseMillis=200 -Djava.util.concurrent.ForkJoinPool.common.parallelism=8 -jar "$JAR_FILE_WITH_DIRECTORY"
}

# Main execution flow
detect_os
run_jar