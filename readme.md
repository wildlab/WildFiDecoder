# WildFi Decoder

The WildFi Decoder is a console application written in Java with the aim to decode the binary [WildFi](https://github.com/trichl/WildFiOpenSource) data collected by ESP32-CAM modules with the appropriate gateway [firmware](https://github.com/trichl/WildFiOpenSource/tree/main/ESP32CAMGateway).  
WildFiDecoderStandalone is well tested and working but will be marked as deprecated as soon as the WildFiDecoderMultiThreaded is tested troughly. 

## Whats new in WildFiDecoderMultiThreaded?
- Decoder now decodes data from individual tags in parallel in seperate decoding threads
- optimised decoding process for proximity and movement data
- Gets executed in the command line from the directory containing the binaries
- progress bars show decoding progress, speed and estimated finish time
- migrated to maven project