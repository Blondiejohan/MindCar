# Mind Controllable Car
With the MCC project, we aim to ease the concept of mind controll by enabling multi-directional movement trough the power of EEG pattern recognition. The project is our take on the 'DIT524 Project: System Development' course held at the University of Gothenburg for second term Software Engeneering and Management Bachalore Programme students.

### Key features
EEG pattern recognition

Machine learning through artificial neural network

User friendly design

Secure user experience (aspiring to be)


### More of the system
The core of our system is an Android application, mainly used for pattern registration and recognition. This application, in turn, talks with a Neurosky Mindwave Mobile headset for reading EEG data and sends movement commands to an Arduino SmartCar device. 


  

### Reference material
The Arduino SmartCar device is directly based on [smartcar](https://github.com/platisd/smartcar)

Our machine learning setup is built using [Neuroph](https://github.com/neuroph/neuroph)

For translating data from the headset we are using the [Thinkgear library](https://github.com/gramant/thinkgear) as a part of the Neurosky Developer Tool for Android.
