#import "HongkaFlutter_pPlugin.h"
#import <hongka_flutter_p/hongka_flutter_p-Swift.h>

@implementation HongkaFlutter_pPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHongkaFlutter_pPlugin registerWithRegistrar:registrar];
}
@end
