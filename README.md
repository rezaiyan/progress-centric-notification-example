# Live Notification Showcase with Android 16 Progress-Centric Style

This project demonstrates the **Progress-Centric Notification (Live Notification)** feature introduced in Android 16. The application simulates an online taxi service where the driver approaches the passenger, and a live notification updates the user with real-time progress. The main focus of this project is implementing **progress-style notifications** using the new `ProgressStyle` feature.

## Features
- **Live Progress Notification**: Displays progress-centric updates as a foreground service, with detailed segments and points representing the driver's journey.
- **Jetpack Compose UI**: The main screen is implemented using Jetpack Compose for a modern and reactive UI.
- **Foreground Service**: Utilizes a `Service` class (`DriverArrivalService`) bound to the notification to handle real-time updates.

## How It Works
The app includes:
1. A `DriverArrivalService` class that simulates a driver's approach by emitting progress updates using a `Flow`.
2. Notifications styled with `ProgressStyle`, displaying:
   - Segments with colors representing different stages of progress.
   - Points highlighting milestones in the driver's journey.
3. Real-time updates using Kotlin Coroutines for smooth notification transitions.

## Core Classes and Functions
### `DriverArrivalService`
Manages the background logic for simulating and updating the live notification.

- **Progress Simulation**: 
  - Simulates a 30-second journey with updates every 16ms.
  - Dynamically adjusts the title, subtitle, and progress of the notification.
- **Foreground Service**: Ensures the notification persists until the simulation is complete.

### `Notification.Builder.applyProgressStyle`
Customizes the notification using the new `ProgressStyle`:
- Adds segments and points with specific colors to visually represent progress.
- Ensures compatibility with Android 16 and higher.

## Screenshots
| **Preview**                |
|----------------------------|
| ![Live Notification Example](https://github.com/user-attachments/assets/4092c88c-9b9a-4796-bf03-386a31287511)|

## Contribute
Contributions are welcome! If you'd like to enhance the app, fix the voting issue, or add new features:
1. Fork the repository.
2. Make your changes.
3. Submit a pull request.

Check out the project walkthrough on YouTube:  
[👉 Watch the video here!](https://youtu.be/EWyl8HjpB_c)

---

Feel free to leave your thoughts or suggestions in the comments section of the video or contribute directly to the codebase. Let's build great things together!

Happy coding!  
**Ali Rezaiyan**  
[Otago Channel](https://www.youtube.com/@OtagoTech?sub_confirmation=1)
