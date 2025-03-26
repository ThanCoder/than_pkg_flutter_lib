enum ScreenOrientationTypes {
  Portrait,
  Landscape;
}

extension ScreenOrientationTypesExtension on ScreenOrientationTypes {
  static ScreenOrientationTypes getType(String name) {
    if (ScreenOrientationTypes.Landscape.name == name) {
      return ScreenOrientationTypes.Landscape;
    }
    return ScreenOrientationTypes.Portrait;
  }

  static String getName(ScreenOrientationTypes type) {
    if (ScreenOrientationTypes.Landscape.name == type.name) {
      return ScreenOrientationTypes.Landscape.name;
    }
    return ScreenOrientationTypes.Portrait.name;
  }
}
