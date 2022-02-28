
## DOCKER COMMANDS

### 1. FIRST BUILD CONTAINER
 
``- docker build -t compiler .``

### 2. RUN APP IN THE CONTAINER '

``- docker run -it --rm --name javacode compiler``

### 3. COPY output file to the host

``- docker cp $CONTAINERAPPID:/zacnikoditAppTests/output.txt .``





