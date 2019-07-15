import Flutter
import UIKit

public class SwiftHongkaFlutter_pPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "hongka_flutter_p", binaryMessenger: registrar.messenger())
    let instance = SwiftHongkaFlutter_pPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
