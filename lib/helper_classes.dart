enum SERVER { L3, L3_US, EMC, EMC_US, CROC, TEST, TEST55 }

class Config {
  static const String sdkKey = "17e58bff68c61e.36519168";
  static const String appID = "206854";
  static const String googleProjectId = "785651527831";
  static const String tenantID = "5963";
  static const server = SERVER.L3;

  static final defaultConfig = [
    Config.sdkKey,
    Config.googleProjectId,
    Config.server,
    Config.appID,
    Config.tenantID
  ];
}
