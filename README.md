## Case Study
> Develop an application has a feature that takes a user comment as input and returns a JSON string containing information about it content:
> - Mention: a way to mention a user. Always starts with an @ and ends when hitting a non-word character.
> - Link: Any URLs contained in the message, along with the pages title.

## Scope:

### Approaching:

When considering the requirements of the case study, my immediate thought is to implement a standalone SDK. There are several reasons why I have reached this decision:

- Firstly, a standalone SDK would be easily extensible, testable, and essential in meeting the requirements of the Case Study: Problem Solving in Java.
- Secondly, I aim to demonstrate my experience in building an SDK with a clean and flexible architecture.
- Lastly, I see this as an opportunity to demonstrate my understanding and experience with the latest frameworks, technologies, and architectures, without being constrained by the specific requirements of the Case Study.

By implementing a standalone SDK, I can address the needs of the case study effectively and highlight my expertise in building robust and adaptable software solutions.

### Functional:

- Ability to takes a user comment as input and returns a JSON string containing information about its contents.
- Standalone SDK written by Java, can be used in any Android application, support sync and async function.
- Application was built with Declarative UI/Reactive programming.
- Good user interface animations and consistent UI elements, demonstrating the requirements from the Case Study.

### Non-Functional:
Scalability:
- Modularity for each layer.

Reliability:
- Fully covered by Unit tests and UI/Intrustrument test.

## Design Decisions:

### UI:
- 100% Jetpack Compose.
- Clean Architecture with 100% Kotlin.
- Presentation layer represented by MVI with Orbit Framework.

### SDK:
- Clean Architect with 100% Java.
- Zero dependencies, leveraging the Android SDK as needed.

### Architecture, Principals and Patterns:
- Clean Architecture
- MVI
- SOLID, Utilizing IoC and DI
- Builder Pattern

### Dependencies:
Product dependencies:
- [Orbit](https://github.com/orbit-mvi/orbit-mvi): Orbit is a Redux/MVI-like library.
- [Koin](https://github.com/InsertKoinIO/koin): Koin is a pragmatic lightweight dependency injection framework for Kotlin developers.
- [Gson](https://github.com/google/gson): Gson is a Java library that can be used to convert Java Objects into their JSON representation.
- [Lottie](https://github.com/airbnb/lottie-android): Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile.

Testing dependencies:
- [Mockito](https://github.com/mockito/mockito): Mockito is mocking framework for unit tests in Java.
- [Mockk](https://github.com/mockk/mockk): MockK is a mocking framework built in Kotlin to be used with Kotlin programs. Because MockK is written in Kotlin, it has first-class support for Kotlin language features such as singleton objects, extension functions, and final classes.
- [PowerMock](https://github.com/powermock/powermock): PowerMock is a framework that extends other mock libraries such as EasyMock/Mockito with more powerful capabilitie.
- [Robolectric](https://github.com/robolectric/robolectric): Robolectric is the industry-standard unit testing framework for Android. With Robolectric, your tests run in a simulated Android environment inside a JVM, without the overhead and flakiness of an emulator.

## Project Structure:

This project has multi modules. For details of each module, please refer to the figure below:

| Name   | Details                                                      |
| ------ | ------------------------------------------------------------ |
| **sdk**   | SDK which is implemented Case Study requirement |
| app    | Implement application user interface and logic. |
| domain | Implement business logic                 |
| data   | Define data structure and implement processing data logic. |
