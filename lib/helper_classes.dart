enum SERVER { L3, L3_US, EMC, EMC_US, CROC, TEST, TEST55 }

class Config{
  static const String sdkKey="17e242cb5c361d.45152518";
  static const String appID="206845";
  static const String googleProjectId="785651527831";
  static const String tenantID="5963";
  static const server=SERVER.L3;

  static final defaultConfig = [
    Config.sdkKey,
    Config.googleProjectId,
    Config.server,
    Config.appID,
    Config.tenantID
  ];
}
