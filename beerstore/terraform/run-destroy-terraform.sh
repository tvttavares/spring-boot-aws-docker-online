#!/bin/bash

MY_PUBLIC_IP="$(curl -s ipinfo.io/ip)"

echo "Destroying terraform..."
terraform destroy -var "my_public_ip=$MY_PUBLIC_IP/32"