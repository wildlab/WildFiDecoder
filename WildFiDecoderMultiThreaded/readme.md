# WildFi Decoder (Multi-threaded)

## Whats new?
    - Decoder now decodes individual tag data in parallel in seperate threads
    - Gets executed in the command line from the directory containing the binaries
    - migrated to maven project
    - progress bars show decoding progress, speed and estimated finish time

## Todos:
    - test with large chunks of data
    - allocate as much heap as possible (right now set to 50%) -> test
    - implement logging option log4j
    - return fatal decoding errors from seperate threads
    - test Shell scripts on Linux / MacOs
    - test Export script for linux / MacOS to automatically add directory to path

## How to setup:
In order to use the decoder [Java](https://www.java.com/en/download/manual.jsp) needs to be installed on your system.
Additionally, the directory with the decoder needs to be added to the system PATH. 

**Windows**     
1. Go to "Environment Variables"
2. Under "System Variables", find the PATH variable, select it, and click "Edit". If there is no PATH variable, click "New".
3. Click on Browse and open the directory of the decoder and apply changes.

In the end the PATH variable should look similar to this:

![EnviromentVariables](/Decoder/WildFiDecoderMultiThreaded/pictures/EnvVar.png?raw=true)

**Mac OS X**

Execute the Export.sh file 

or add it manually: 
1. Open the `.bash_profile` file in your home directory (for example, `/Users/your-user-name/.bash_profile`) in a text editor.
2. Add `export PATH="your-dir:$PATH"` to the last line of the file, where your-dir is the directory you want to add.
3. Save the `.bash_profile` file.

**Linux**

Execute the Export.sh file

or add it manually:
1. Open the `.bashrc` file in your home directory (for example, `/home/your-user-name/.bashrc`) in a text editor.
2. Add `export PATH="your-dir:$PATH"` to the last line of the file, where your-dir is the directory you want to add.
3. Save the `.bashrc` file.

## How to use:
In order to use the decoder, open a console or powershell and navigate to your directory containing the binary data.
You can also open the console/powershell in the directory of your data by holding shift and right-click and then choose `open powershell here`.
Under Windows you then need to execute the `WildFiDecoderMT.bat` and under Linux / Mac OS X the `WildFiDecoderMT.sh`.

Depending on your operating system the decoder should start up looking like this:

![EnviromentVariables](/Decoder/WildFiDecoderMultiThreaded/pictures/PowerShell.png?raw=true)