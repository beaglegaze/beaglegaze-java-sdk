#!/bin/bash

cd src/main/resources
solcjs UsageContract.sol --abi --bin
cd ../../../
web3j generate solidity -b src/main/resources/UsageContract_sol_UsageContract.bin -a src/main/resources/UsageContract_sol_UsageContract.abi -o src/main/java -p web3.beaglegaze
mvn clean compile