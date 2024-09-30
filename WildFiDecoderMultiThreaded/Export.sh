#!/bin/bash

# Detect the current shell profile file
if [ -f ~/.bashrc ]; then
    PROFILE_FILE=~/.bashrc
elif [ -f ~/.bash_profile ]; then
    PROFILE_FILE=~/.bash_profile
elif [ -f ~/.zshrc ]; then
    PROFILE_FILE=~/.zshrc
else
    echo "No compatible shell profile file found."
    exit 1
fi

# Add the current directory to PATH in the profile file if not already added
CURRENT_DIR=$(pwd)
if ! grep -q "export PATH=\"\$PATH:$CURRENT_DIR\"" "$PROFILE_FILE"; then
    echo "export PATH=\"\$PATH:$CURRENT_DIR\"" >> "$PROFILE_FILE"
    echo "Added current directory to PATH in $PROFILE_FILE"
else
    echo "Current directory is already in PATH."
fi

# Source the profile file to apply changes immediately
source "$PROFILE_FILE"

# Confirm the PATH has been updated
echo "Updated PATH: $PATH"