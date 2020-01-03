# Buggy calculator for demonstration purposes

I wrote this calculator to have a simple app for automation and also to demonstrate some common bugs in Android apps.

## Known bugs
* Invalid binary operation results in a crash. _e.g., 1+=_
* In Landscape mode, you don't see lower buttons.

## Compiling and running

MoQuality android-library dependency is needed for automation support.
```
cd <dir>
git clone https://github.com/moquality/android-library
git clone https://github.com/shauvik/Calculator
cd Calculator
```

Compile
```
./gradlew assembleDebug assembleDebugAndroidTest
```

Run all espresso tests
```
./gradlew cAT
```

Run just the robo test
```
./gradlew -Pandroid.testInstrumentationRunnerArguments.class=com.shauvik.calc.CalcEspressoTest#roboTest cAT
```