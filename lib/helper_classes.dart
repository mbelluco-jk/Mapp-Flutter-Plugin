enum SERVER { L3, L3_US, EMC, EMC_US, CROC, TEST, TEST55 }

class Config{
  static const String sdkKey="1816b634b0c62a.20197038";
  static const String appID="300861";
  static const String googleProjectId="785651527831";
  static const String tenantID="33";
  static const server=SERVER.TEST;

  static final defaultConfig = [
    Config.sdkKey,
    Config.googleProjectId,
    Config.server,
    Config.appID,
    Config.tenantID
  ];
}
