#!/bin/bash

cd src/main/resources
solcjs UsageContract.sol --abi --bin
cd ../../../
web3j generate solidity -b src/main/resources/Beaglegaze_sol_Beaglegaze.bin -a src/main/resources/Beaglegaze_sol_Beaglegaze.abi -o src/main/java -p web3.beaglegaze
mvn clean compile