#!/bin/sh

echo "Waiting for Oracle"
echo -e "\nOracle ready"

echo -e "CREATE USER quill IDENTIFIED BY oracle" | sqlplus -s system/oracle &> /dev/null
echo -e "GRANT DBA TO quill" | sqlplus -s system/oracle &> /dev/null
