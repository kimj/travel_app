# Implementation Plan - Map Explore ViewModel

I will build a Hilt Jetpack ViewModel for the Map Explore feature to separate state logic from the UI view functions.

## Proposed Changes

### [app](file:///Users/kimj/proj/travel_app/app)

#### [NEW] [MapExploreViewModel.kt](file:///Users/kimj/proj/travel_app/app/src/main/java/com/mentalmachines/travel_app/ui/map/MapExploreViewModel.kt)
- Create `MapExploreViewModel` class annotated with `@HiltViewModel`.
- Define `MapExploreUiState` holding the list of recommended places and the currently active/selected place.
- Implement an `selectPlace(InterestPlace)` method to switch active markers.

#### [MODIFY] [MapExploreScreen.kt](file:///Users/kimj/proj/travel_app/app/src/main/java/com/mentalmachines/travel_app/ui/map/MapExploreScreen.kt)
- Update `MapExploreScreen` page function to consume the new `MapExploreViewModel` via `hiltViewModel()`.

---

## Verification Plan

### Automated Tests
- Build verification via:
  ```bash
  ./gradlew :app:assembleDebug
  ```

### Manual Verification
- Open the explore map screen, tap different pins, and confirm selection state changes remain responsive via the newly introduced ViewModel stream.
