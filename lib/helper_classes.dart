enum SERVER { L3, L3_US, EMC, EMC_US, CROC, TEST, TEST55 }

class Config{
  static const String sdkKey="17e243d300361d.38133363";
  static const String appID="206846";
  static const String googleProjectId="785651527831";
  static const String tenantID="7681";
  static const server=SERVER.L3;

  static final defaultConfig = [
    Config.sdkKey,
    Config.googleProjectId,
    Config.server,
    Config.appID,
    Config.tenantID
  ];
}
