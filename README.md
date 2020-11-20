# RestaurantApp

Sample Restaurant App like PastryShop

## Features:
- Beautiful animations.
- Offline first approach. All the network responses are cached into the database (powered by [Room](https://developer.android.com/topic/libraries/architecture/room)).
- Uses android architecture components.
- Follows MVVM architecture pattern.
- Uses dagger heavily to provide clear separation between different architecture layers. Helps to write testable code.
- Mocking database, shared preferences and web server to write accurate tests.
- Single activity application.

## How to run tests?
- Make sure you connect physical device or run an emulator before running the UI tests.
- To run tests, run following command:
```bash
./gradlew cAT
```

## Acknowledgement:
- Code coverage currently displays percentage of code tested by the unit tests.

## Screenshots:

|Portrait (Pixel 2)|Landscape (Nexus 9)|
|:---:|:---:|
|![portrait.gif](/.github/portrait.gif)|![landscape.gif](/.github/landscape.gif)|


## License
Copyright 2020 Zeynel Erdi Karabulut

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
