enum SERVER { L3, L3_US, EMC, EMC_US, CROC, TEST, TEST55 }

class Config{
  static const String sdkKey="17e246d494161d.59387323";
  static const String appID="310421";
  static const String googleProjectId="785651527831";
  static const String tenantID="60211";
  static const server=SERVER.EMC_US;

  static final defaultConfig=[Config.sdkKey, Config.googleProjectId, Config.server, Config.appID, Config.tenantID];
}

