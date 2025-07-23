#!/bin/bash
cd /home/kavia/workspace/code-generation/note-management-system-c06cc6c9/notes_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

