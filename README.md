Image Sorter
============

Little Spring Integration project to copy photo from a folder to another folder sorted by year and month of the photo creation.
The program works as a daemon when is running it continue to scan the folder.

Usage : 

- clone the project.
- gradlew installDist
- Go to `build/install`
- In terminal
    - set Environment variable `IMAGE_SORTER_OPTS=-Dinput.folder=input_folder -Doutput.folder=output_folder`
        - linux : export set "IMAGE_SORTER_OPTS=-Dinput.folder=input_folder -Doutput.folder=output_folder"  
        - windows : IMAGE_SORTER_OPTS="-Dinput.folder=input_folder -Doutput.folder=output_folder"
    - execute `./image-sorter/bin/image-sorter`