import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:hongka_flutter_p/src/hongka_flutter_p.dart';

void main() {
  const MethodChannel channel = MethodChannel('hongka_flutter_p');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await HongkaFlutter_p.platformVersion, '42');
  });
}
