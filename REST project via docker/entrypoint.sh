#!/bin/sh
# Wait for 40 seconds
sleep 20

# Execute the container's main command
exec "$@"
