# WildFi Decoder (Multi-threaded)

## Todos:
- compare decoded files with old decoder to double check
- test potential fatal erros with `LINE_BUFFER_SIZE` -> especially if 2000 is to small for prox
- test with large chunks of data
- allocate as much heap as possible (right now set to 50%) -> test
- implement logging option log4j
- return fatal decoding errors from seperate threads
- Test Decoder on MacOs
- test Export script for MacOS to automatically add directory to path

## How to setup:
In order to use the decoder [Java](https://www.java.com/en/download/manual.jsp) needs to be installed on your system.
Additionally, the directory with the decoder needs to be added to the system PATH. 

**Windows**     
1. Go to "Environment Variables"
2. Under "System Variables", find the PATH variable, select it, and click "Edit". If there is no PATH variable, click "New".
3. Click on Browse and open the directory of the decoder and apply changes.

In the end the PATH variable should look similar to this:

![EnviromentVariables](/WildFiDecoderMultiThreaded/pictures/EnvVar.png?raw=true)

**Mac OS X**

First make the two shell scripts `Export.sh` and `WildFiDecoderMT.sh` executable with the command `chmod +x Export.sh` and `chmod +x WildFiDecoderMT.sh`. 
Then run `bash Export.sh` to execute the script that automatically adds the directory to your PATH variable. After that close your terminal and re-open it and run `echo $PATH`. The directory ot the decoder should now be added to your system PATH.

If this isnt the case you can do it manually:
1. Open the `.bash_profile` file in your home directory (for example, `/Users/your-user-name/.bash_profile`) in a text editor.
2. Add `export PATH="your-dir:$PATH"` to the last line of the file, where your-dir is the directory you want to add.
3. Save the `.bash_profile` file.

**Linux**

First make the two shell scripts `Export.sh` and `WildFiDecoderMT.sh` executable with the command `chmod +x Export.sh` and `chmod +x WildFiDecoderMT.sh`. 
Then run `bash Export.sh` to execute the script that automatically adds the directory to your PATH variable. After that close your terminal and re-open it and run `echo $PATH`. The directory ot the decoder should now be added to your system PATH.

If this isnt the case you can do it manually:
1. Open the `.bashrc` file in your home directory (for example, `/home/your-user-name/.bashrc`) in a text editor.
2. Add `export PATH="your-dir:$PATH"` to the last line of the file, where your-dir is the directory you want to add.
3. Save the `.bashrc` file.

## How to use:
In order to use the decoder, open a console or powershell and navigate to your directory containing the binary data.
You can also open the console/powershell in the directory of your data by holding shift and right-click and then choose `open powershell here`.
Under Windows you then need to execute the `WildFiDecoderMT.bat` and under Linux / Mac OS X run `bash WildFiDecoderMT.sh`.

Depending on your operating system the decoder should start up looking like this:

![EnviromentVariables](/WildFiDecoderMultiThreaded/pictures/PowerShell.png?raw=true)
